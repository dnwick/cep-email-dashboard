import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.config.SiddhiConfiguration;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;
import org.wso2.siddhi.extension.*;

import java.util.ArrayList;
import java.util.List;

public class ExternalTimeBatchWindowTestCase {

    static final Logger log = Logger.getLogger(ExternalTimeBatchWindowTestCase.class);

    private int count;
    private long value;
    private boolean eventArrived;

    @Before
    public void init() {
        count = 0;
        value = 0;
        eventArrived = false;
    }

    @Test
    public void testWindowQuery1() throws InterruptedException {
        SiddhiConfiguration conf = new SiddhiConfiguration();
        List<Class> classList = new ArrayList<Class>();
        classList.add(StringConcatAggregatorFactory.class);
        classList.add(StringConcatAggregatorString.class);
        classList.add(UniqueCountAggregatorFactory.class);
        classList.add(UniqueCountAggregatorLong.class);
        classList.add(ExternalTimeBatchWindow.class);

        conf.setSiddhiExtensions(classList);

        log.info("ExternalTime Window test1");

        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.getSiddhiContext().setSiddhiExtensions(classList);

        InputHandler inputHandler = siddhiManager.defineStream("define stream LoginEvents (timeStamp long, ip string) ");

        String queryReference = siddhiManager.addQuery("from LoginEvents#window.custom:externalTimeBatch(timeStamp,5 sec) " +
                "select timeStamp, ip " +
                "insert into uniqueIps for all-events ;");

        siddhiManager.addCallback(queryReference, new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                if (inEvents != null){
                   count = count+inEvents.length;
                } else{
                   value = value+removeEvents.length;
                }
                eventArrived = true;
            }
        });
        System.out.println(System.currentTimeMillis());

        inputHandler.send(new Object[]{1366335804341l, "192.10.1.3"});
        inputHandler.send(new Object[]{1366335804342l, "192.10.1.4"});
        inputHandler.send(new Object[]{1366335814341l, "192.10.1.5"});
        inputHandler.send(new Object[]{1366335814345l, "192.10.1.6"});
        inputHandler.send(new Object[]{1366335824341l, "192.10.1.7"});


        Thread.sleep(50000);

        Assert.assertEquals("Event arrived", true, eventArrived);
        Assert.assertEquals("In Events ", 5, count);
        Assert.assertEquals("Remove Events ", 4, value);
        siddhiManager.shutdown();
    }






}
