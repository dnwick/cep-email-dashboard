package org.wso2.cep.email.monitor.query.compiler;


import junit.framework.Assert;
import org.wso2.cep.email.monitor.query.compiler.exeception.EmailMonitorCompilerException;
import org.wso2.cep.email.monitor.query.compiler.internal.QueryManagerService;
import org.junit.Test;

public class QueryManagerServiceTest {


    private QueryManagerService queryManagerService;




    @Test
   public  void testfilterQuerywithTo() throws EmailMonitorCompilerException {
     queryManagerService = new QueryManagerService();
      String[] result = queryManagerService.getSiddhiQuery(" to : (abc@wso2.com ) add label importnant" );
        Assert.assertEquals(2,result.length);
       Assert.assertEquals("from gmailInputStream select  threadID,labels, email:getAll(to) as to ,email:getAll(sender) as senders, \"importnant\" as newLabel insert into filteredEmailDetails;",result[0]);
       Assert.assertEquals("from filteredEmailDetails[(to contains \"abc@wso2.com\")] select threadID, newLabel as label insert into gmailOutputStream;",result[1]);
   }

    @Test
    public  void testfilterQuerywithlabel() throws EmailMonitorCompilerException {
        queryManagerService = new QueryManagerService();
        String[] result = queryManagerService.getSiddhiQuery(" label : (marketing) add label importnant" );
        Assert.assertEquals(2,result.length);
        Assert.assertEquals("from gmailInputStream select  threadID,labels, email:getAll(to) as to ,email:getAll(sender) as senders, \"importnant\" as newLabel insert into filteredEmailDetails;",result[0]);
        Assert.assertEquals("from filteredEmailDetails[(labels contains \"marketing\")] select threadID, newLabel as label insert into gmailOutputStream;",result[1]);
    }
    @Test
    public  void testfilterQuerywithfrom() throws EmailMonitorCompilerException {
        queryManagerService = new QueryManagerService();
        String[] result = queryManagerService.getSiddhiQuery(" from : (abc@wso2.com ) add label importnant" );
        Assert.assertEquals(2,result.length);
        Assert.assertEquals("from gmailInputStream select  threadID,labels, email:getAll(to) as to ,email:getAll(sender) as senders, \"importnant\" as newLabel insert into filteredEmailDetails;",result[0]);
        Assert.assertEquals("from filteredEmailDetails[(senders contains \"abc@wso2.com\")] select threadID, newLabel as label insert into gmailOutputStream;",result[1]);
    }

    @Test
    public  void testfilterQuerywithfrequency() throws EmailMonitorCompilerException{
        queryManagerService = new QueryManagerService();
        String[] result = queryManagerService.getSiddhiQuery("frequency >3/d add  label important" );
        Assert.assertEquals(2,result.length);
        Assert.assertEquals("from gmailInputStream#window.externalTime(sentDate,1 days) select  threadID, labels,count(messageID) as emailCount, email:getAll(to) as to ,email:getAll(sender) as senders, \"important\" as newLabel group by threadID  having emailCount == 4 insert into threadDetails;",result[0]);
        Assert.assertEquals("from threadDetails select threadID, newLabel as label insert into gmailOutputStream;",result[1]);
    }

    @Test
    public  void testfilterQuerywithfilteringandfrequnecy() throws EmailMonitorCompilerException {
        queryManagerService = new QueryManagerService();
        String[] result = queryManagerService.getSiddhiQuery("to:(abc@wso2.com and def@wso2.com or ghi@wso2.com) and label :(marketing) or from:(ijk@wso2.com) and frequency >3/d add  label important" );
        Assert.assertEquals(2,result.length);
        Assert.assertEquals("from gmailInputStream#window.externalTime(sentDate,1 days) select  threadID,labels, count(messageID) as emailCount, email:getAll(to) as to ,email:getAll(sender) as senders, \"important\" as newLabel group by threadID  having emailCount == 4 insert into threadDetails;",result[0]);
        Assert.assertEquals("from threadDetails[(to contains \"abc@wso2.com\" and to contains \"def@wso2.com\" or to contains \"ghi@wso2.com\") and (labels contains \"marketing\")or(senders contains \"ijk@wso2.com\")] select threadID, newLabel as label insert into gmailOutputStream;",result[1]);

    }


    @Test
    public  void testfilterQuerywithfilteringandlabelfrequnecy() throws EmailMonitorCompilerException {
        queryManagerService = new QueryManagerService();
        String[] result = queryManagerService.getSiddhiQuery("to:(abc@wso2.com and def@wso2.com or ghi@wso2.com) and label :(marketing) or from:(ijk@wso2.com) and thread frequency >3/d send mail (to : isurur@wso2.com subject : important body : importantBody)" );
        Assert.assertEquals(2,result.length);
        Assert.assertEquals("from gmailInputStream[(labels contains \"marketing\")]#window.externalTime(sentDate,1 days) select   email:getUniqueCount(threadID) as threadCount, \"isurur@wso2.com\"  as to, \"important\"  as subject, \"importantBody\"  as content, \"marketing\"  as label insert into labelDetails;",result[0]);
        Assert.assertEquals("from labelDetails [(threadCount == 4 )]select  * insert into emailSenderOutputStream;",result[1]);

    }


    @Test
    public  void testfilterQuerywithToFrom() throws EmailMonitorCompilerException {
        queryManagerService = new QueryManagerService();
        String[] result = queryManagerService.getSiddhiQuery(" to : (abc@wso2.com ) and from:(def@wso2.com) add label importnant" );
        Assert.assertEquals(2,result.length);
        Assert.assertEquals("from gmailInputStream select  threadID,labels, email:getAll(to) as to ,email:getAll(sender) as senders, \"importnant\" as newLabel insert into filteredEmailDetails;",result[0]);
        Assert.assertEquals("from filteredEmailDetails[(to contains \"abc@wso2.com\")and(senders contains \"def@wso2.com\")] select threadID, newLabel as label insert into gmailOutputStream;",result[1]);
    }

    @Test
    public  void testfilterQuerywithfilteringandlabelfrequnecywithExternalTimeBatch() throws EmailMonitorCompilerException {
        queryManagerService = new QueryManagerService();
        String[] result = queryManagerService.getSiddhiQuery("to:(abc@wso2.com and def@wso2.com or ghi@wso2.com) and label :(marketing) or from:(ijk@wso2.com) and thread frequency < 3/d send mail (to : isurur@wso2.com subject : important body : importantBody)" );
        Assert.assertEquals(2,result.length);
        Assert.assertEquals("from gmailInputStream[(labels contains \"marketing\")]#window.custom:externalTimeBatch(sentDate,1 days) select   email:getUniqueCount(threadID) as threadCount, \"isurur@wso2.com\"  as to, \"important\"  as subject, \"importantBody\"  as content, \"marketing\"  as label insert into labelDetails;",result[0]);
        Assert.assertEquals("from labelDetails [(threadCount < 3 )]select  * insert into emailSenderOutputStream;",result[1]);

    }













































}
