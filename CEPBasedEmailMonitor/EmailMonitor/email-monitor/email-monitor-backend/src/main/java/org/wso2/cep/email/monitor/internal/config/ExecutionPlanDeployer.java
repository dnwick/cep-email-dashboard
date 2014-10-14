package org.wso2.cep.email.monitor.internal.config;


import org.apache.axis2.engine.AxisConfiguration;
import org.apache.log4j.Logger;
import org.wso2.carbon.event.processor.core.EventProcessorService;
import org.wso2.carbon.event.processor.core.exception.ExecutionPlanConfigurationException;
import org.wso2.carbon.event.processor.core.exception.ExecutionPlanDependencyValidationException;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.internal.config.esb.config.XMLReader;
import org.wso2.cep.email.monitor.internal.ds.EmailMonitorValueHolder;
import org.wso2.cep.email.monitor.internal.util.EmailMonitorConstants;


public class ExecutionPlanDeployer {

    private static final Logger logger = Logger.getLogger(ExecutionPlanDeployer.class);
    private EventProcessorService eventProcessorService;
    private XMLReader xmlReader;

    public ExecutionPlanDeployer() {

        xmlReader = new XMLReader();
        EmailMonitorValueHolder emailMonitorValueHolder = EmailMonitorValueHolder.getInstance();
        eventProcessorService = emailMonitorValueHolder.getEventProcessorService();
    }


    public String createExecutionPlan(String[] queries , AxisConfiguration axisConfiguration) throws EmailMonitorServiceException {
        String executionPlanXmlConfiguration = this.getExecutionPlanConfiguration(queries);
        String executionPlanName = this.getExecutionPlanName();
        executionPlanXmlConfiguration = executionPlanXmlConfiguration.replace(EmailMonitorConstants.EXECUTION_PLAN_NAME,executionPlanName);

        try {

            eventProcessorService.deployExecutionPlanConfiguration(executionPlanXmlConfiguration, axisConfiguration);
        } catch (ExecutionPlanDependencyValidationException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding execution plan", e);
        } catch (ExecutionPlanConfigurationException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding execution plan", e);
        }
        return executionPlanName;
    }


    private String getExecutionPlanConfiguration(String[] queries) throws EmailMonitorServiceException {
        String  content = xmlReader.readXML(EmailMonitorConstants.EXECUTION_PLAN_TEMPLATE);
        for (int i = 0; i < queries.length; i++) {
            String[] queryInfo = queries[i].split("\\s+|\\[|#|;");
            String inputStream = queryInfo[1];


            String queryOutputPart = queries[i].substring(queries[i].indexOf("insert into"));
            String[] arry = queryOutputPart.split("\\s+|\\[|#|;");
            String outputStream = arry[2];

            content = content.replaceFirst(EmailMonitorConstants.ADD_QUERY, queries[i]);

            if (i == 0) {
                content = content.replaceAll(EmailMonitorConstants.INPUT_STREAM_NAME, inputStream);
            }
            content = content.replaceAll(EmailMonitorConstants.OUTPUT_STREAM_NAME + i, outputStream);

        }
        return content;
    }


    private String getExecutionPlanName(){
        RegistryManager registryManager = new RegistryManager();
        int queryCount = Integer.parseInt(registryManager.getResourceString(EmailMonitorConstants.REGISTRY_QUERYCOUNT_RESOURCE_PATH));
        queryCount++;
        registryManager.saveResourceString(String.valueOf(queryCount), EmailMonitorConstants.REGISTRY_QUERYCOUNT_RESOURCE_PATH);
        return EmailMonitorConstants.ADD_EXECUTION_PLAN_NAME+ queryCount;

    }





}



