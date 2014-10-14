package org.wso2.siddhi.extension;


import org.apache.log4j.Logger;
import org.wso2.siddhi.core.config.SiddhiContext;
import org.wso2.siddhi.core.event.StreamEvent;
import org.wso2.siddhi.core.event.in.InEvent;
import org.wso2.siddhi.core.event.in.InListEvent;
import org.wso2.siddhi.core.event.remove.RemoveEvent;
import org.wso2.siddhi.core.event.remove.RemoveListEvent;
import org.wso2.siddhi.core.query.QueryPostProcessingElement;
import org.wso2.siddhi.core.query.processor.window.WindowProcessor;
import org.wso2.siddhi.core.util.collection.queue.SiddhiQueue;
import org.wso2.siddhi.core.util.collection.queue.SiddhiQueueGrid;
import org.wso2.siddhi.query.api.definition.AbstractDefinition;
import org.wso2.siddhi.query.api.expression.Expression;
import org.wso2.siddhi.query.api.expression.Variable;
import org.wso2.siddhi.query.api.expression.constant.IntConstant;
import org.wso2.siddhi.query.api.expression.constant.LongConstant;
import org.wso2.siddhi.query.api.extension.annotation.SiddhiExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

@SiddhiExtension(namespace = "custom", function = "externalTimeBatch")
public class ExternalTimeBatchWindow extends WindowProcessor {
    static final Logger log = Logger.getLogger(ExternalTimeBatchWindow.class);
    private List<InEvent> newEventList;
    private List<RemoveEvent> oldEventList;
    private long timeToKeep;
    private SiddhiQueue<StreamEvent> window;
    private String timeStampAttributeName;
    private int timeStampAttributePosition;
    private long scheduledTime;
    private  Long lastrecivedevent;



    @Override
    protected void processEvent(InEvent inEvent){
        acquireLock();
        try {
            long lastrecivedevent = (Long) inEvent.getData(timeStampAttributePosition);
            removeExpiredEventBatch();
            newEventList.add(inEvent);
            }finally{
            releaseLock();
            }
    }

    @Override
    protected void processEvent(InListEvent inListEvent){
        acquireLock();
        try {
            long currentTime = (Long) inListEvent.getEvent(inListEvent.getActiveEvents() - 1).getData(timeStampAttributePosition);
            for (int i = 0, activeEvents = inListEvent.getActiveEvents(); i < activeEvents; i++){
                newEventList.add((InEvent)inListEvent.getEvent(i));
            }
            removeExpiredEventBatch();
            nextProcessor.process(inListEvent);
        }finally{
            releaseLock();
        }
    }

    public void removeExpiredEventBatch() {

       if(newEventList.size()==0){
           scheduledTime = System.currentTimeMillis();
       }
        long diff = timeToKeep -(System.currentTimeMillis() -scheduledTime);
        while (true) {
            if(diff>0){
                break;
            }else{
                if(newEventList.size()>0){
                    InEvent[] inEvents = newEventList.toArray(new InEvent[newEventList.size()]);
                    for (InEvent inEvent : inEvents) {

                        window.put(new RemoveEvent(inEvent, (Long) inEvent.getData(timeStampAttributePosition) + timeToKeep));
                    }
                    nextProcessor.process(new InListEvent(inEvents));
                    for (InEvent inEvent : inEvents){
                        removeExpiredEvent( (Long) inEvent.getData(timeStampAttributePosition));

                    }
                    newEventList.clear();
                    scheduledTime = System.currentTimeMillis();
                }
                break;
            }
        }
    }

    private void removeExpiredEvent(long currentTime) {
        while (true){
            RemoveEvent expiredEvent = (RemoveEvent) window.peek();
            if (expiredEvent != null && expiredEvent.getExpiryTime() < currentTime) {
                oldEventList.add(expiredEvent);
                window.poll();
            } else {
                if(oldEventList.size()>0){
                    nextProcessor.process(new RemoveListEvent(oldEventList.toArray(new RemoveEvent[oldEventList.size()])));
                    oldEventList.clear();
                }
                break;
            }
        }
    }


    @Override
    public Iterator<StreamEvent> iterator() {
        return null;
    }

    @Override
    public Iterator<StreamEvent> iterator(String s) {
        return null;
    }

    @Override
    protected Object[] currentState() {
        return new Object[0];
    }

    @Override
    protected void restoreState(Object[] objects) {

    }

    @Override
    protected void init(Expression[] expressions, QueryPostProcessingElement queryPostProcessingElement, AbstractDefinition abstractDefinition, String s, boolean b, SiddhiContext siddhiContext) {
        if (parameters[1] instanceof IntConstant) {
            timeToKeep = ((IntConstant) parameters[1]).getValue();
        } else {
            timeToKeep = ((LongConstant) parameters[1]).getValue();
        }
        timeStampAttributeName = ((Variable) parameters[0]).getAttributeName();
        timeStampAttributePosition = definition.getAttributePosition(timeStampAttributeName);


        oldEventList = new ArrayList<RemoveEvent>();
        if (this.siddhiContext.isDistributedProcessingEnabled()) {
            newEventList = this.siddhiContext.getHazelcastInstance().getList(elementId + "-newEventList");
        } else {
            newEventList = new ArrayList<InEvent>();
        }
        if (this.siddhiContext.isDistributedProcessingEnabled()) {
            window = new SiddhiQueueGrid<StreamEvent>(elementId, this.siddhiContext, this.async);
        } else {
            window = new SiddhiQueue<StreamEvent>();
        }


    }
    @Override
    public void destroy() {

    }


    ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(1);

    ScheduledFuture scheduledFuture =
            scheduledExecutorService.schedule(new Callable() {
                                                  public Object call() throws Exception {
                                                     removeExpiredEventBatch();
                                                     return "Called!";
                                                  }
                                              },
                    timeToKeep+10,
                    TimeUnit.SECONDS
            );

}
