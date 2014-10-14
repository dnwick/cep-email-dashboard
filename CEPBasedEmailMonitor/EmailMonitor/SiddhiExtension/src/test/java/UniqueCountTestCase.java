import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.config.SiddhiConfiguration;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;
import org.wso2.siddhi.extension.*;
import org.wso2.siddhi.query.api.QueryFactory;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.expression.Expression;
import org.wso2.siddhi.query.api.query.Query;

import java.util.ArrayList;
import java.util.List;


public class UniqueCountTestCase {
    static final Logger log = Logger.getLogger(UniqueCountTestCase.class);

    private int count;
    private boolean eventArrived;

    @Before
    public void init() {
        count = 0;
        eventArrived = false;
    }

    @Test
    public void testUniqueCount1() throws Exception {
        log.info("testUniqueCount1");


        SiddhiConfiguration conf = new SiddhiConfiguration();
        List<Class> classList = new ArrayList<Class>();
        classList.add(StringConcatAggregatorFactory.class);
        classList.add(StringConcatAggregatorString.class);
        classList.add(UniqueCountAggregatorFactory.class);
        classList.add(UniqueCountAggregatorLong.class);

        conf.setSiddhiExtensions(classList);

        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.getSiddhiContext().setSiddhiExtensions(classList);



        siddhiManager.defineStream("define stream emailStream (label string, to string, threadID long, messageID long) ");

        String queryReference = siddhiManager.addQuery("from emailStream#window.time(1 hour) " +
                "select threadID , email:getUniqueCount(messageID) as messageCount " +
                "group by threadID " +
                "insert into mailOutput;");




        siddhiManager.addCallback(queryReference, new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count++;
//               junit.framework.Assert.assertTrue("1".equals(inEvents[0].getData(1).toString()));

                eventArrived = true;
            }
        });
        InputHandler inputHandler = siddhiManager.getInputHandler("emailStream");
        inputHandler.send(new Object[]{"sales", "abc@gmail.com", 100l, 12l});
        inputHandler.send(new Object[]{"sales", "cde@gmail.com", 110l, 123l});
        Thread.sleep(1000);
        inputHandler.send(new Object[]{"sales", "fgh@gmail.com", 100l, 21l});
        inputHandler.send(new Object[]{"support", "asc@gmail.com", 100l, 21l});
        Thread.sleep(100);
        junit.framework.Assert.assertEquals(4, count);
        junit.framework.Assert.assertEquals("Event arrived", true, eventArrived);
        siddhiManager.shutdown();

    }


    @Test
    public void testUniqueCount2() throws Exception {
        log.info("testUniqueCount2");


        SiddhiConfiguration conf = new SiddhiConfiguration();
        List<Class> classList = new ArrayList<Class>();
        classList.add(UniqueCountAggregatorFactory.class);
        classList.add(UniqueCountAggregatorLong.class);
        classList.add(ExternalTimeBatchWindow.class);
        conf.setSiddhiExtensions(classList);

        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.getSiddhiContext().setSiddhiExtensions(classList);

        siddhiManager.defineStream("define stream emailStream (label string, to string, threadID long, messageID long) ");

        String queryReference = siddhiManager.addQuery("from emailStream#window.time(1 sec) " +
                "select threadID , email:getUniqueCount(messageID) as messageCount " +
                "group by threadID " +
                "insert into mailOutput;");

        siddhiManager.addCallback(queryReference, new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count++;
                if (count == 1) {
                    junit.framework.Assert.assertTrue("1".equals(inEvents[0].getData(1).toString()));
                }
                else if (count == 2) {
                    junit.framework.Assert.assertTrue("1".equals(inEvents[0].getData(1).toString()));
                }
                else if (count == 3) {
                    junit.framework.Assert.assertTrue("2".equals(inEvents[0].getData(1).toString()));
                }
                else if (count == 4) {
                    junit.framework.Assert.assertTrue("3".equals(inEvents[0].getData(1).toString()));
                }
                else if (count == 5) {
                    junit.framework.Assert.assertTrue("3".equals(inEvents[0].getData(1).toString()));
                }
                else if (count == 6) {
                    junit.framework.Assert.assertTrue("4".equals(inEvents[0].getData(1).toString()));
                }
                eventArrived = true;
            }
        });
        InputHandler inputHandler = siddhiManager.getInputHandler("emailStream");
        inputHandler.send(new Object[]{"sales", "abc@gmail.com", 100l, 12l});
        inputHandler.send(new Object[]{"sales", "cde@gmail.com", 110l, 123l});
        inputHandler.send(new Object[]{"sales", "fgh@gmail.com", 100l, 21l});
        inputHandler.send(new Object[]{"support", "asc@gmail.com", 100l, 45l});
        inputHandler.send(new Object[]{"support", "asc@gmail.com", 100l, 45l});
        inputHandler.send(new Object[]{"support", "asc@gmail.com", 100l, 05l});
        Thread.sleep(100);
        junit.framework.Assert.assertEquals(6, count);
        junit.framework.Assert.assertEquals("Event arrived", true, eventArrived);
        siddhiManager.shutdown();

    }

}
