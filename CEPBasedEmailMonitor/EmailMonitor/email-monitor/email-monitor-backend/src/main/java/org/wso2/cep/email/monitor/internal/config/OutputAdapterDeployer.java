package org.wso2.cep.email.monitor.internal.config;



import org.apache.axis2.engine.AxisConfiguration;
import org.apache.log4j.Logger;
import org.wso2.carbon.event.output.adaptor.core.config.OutputEventAdaptorConfiguration;
import org.wso2.carbon.event.output.adaptor.manager.core.OutputEventAdaptorManagerService;
import org.wso2.carbon.event.output.adaptor.manager.core.exception.OutputEventAdaptorManagerConfigurationException;

import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.internal.ds.EmailMonitorValueHolder;
import org.wso2.cep.email.monitor.internal.util.EmailMonitorConstants;


public class OutputAdapterDeployer {


    private static final Logger logger = Logger.getLogger(OutputAdapterDeployer.class);
    private OutputEventAdaptorManagerService outputEventAdaptorManagerService;



    public OutputAdapterDeployer() throws EmailMonitorServiceException {

        EmailMonitorValueHolder emailMonitorValueHolder = EmailMonitorValueHolder.getInstance();
        outputEventAdaptorManagerService = emailMonitorValueHolder.getOutputEventAdaptorManagerService() ;
    }

    public void createSoapOutputAdapter(AxisConfiguration axisConfiguration) throws EmailMonitorServiceException {
        try {
            outputEventAdaptorManagerService.getActiveOutputEventAdaptorConfigurationContent(EmailMonitorConstants.SOAP_OUTPUT_ADAPTER_NAME,axisConfiguration);
        } catch (OutputEventAdaptorManagerConfigurationException e) {
            OutputEventAdaptorConfiguration outputEventAdaptorConfiguration = new OutputEventAdaptorConfiguration();
            outputEventAdaptorConfiguration.setName(EmailMonitorConstants.SOAP_OUTPUT_ADAPTER_NAME);
            outputEventAdaptorConfiguration.setType(EmailMonitorConstants.ADAPTER_TYPE_SOAP);
            try {
                outputEventAdaptorManagerService.deployOutputEventAdaptorConfiguration(outputEventAdaptorConfiguration, axisConfiguration);
            } catch (OutputEventAdaptorManagerConfigurationException ex) {
                logger.error(ex.getMessage());
                throw new EmailMonitorServiceException("Error when adding output adapter", ex);
            }
        }

    }

    public void createEmailOutputAdapter(AxisConfiguration axisConfiguration) throws EmailMonitorServiceException {

        try {
            outputEventAdaptorManagerService.getActiveOutputEventAdaptorConfigurationContent(EmailMonitorConstants.EMAIL_OUTPUT_ADAPTER_NAME,axisConfiguration);
        } catch (OutputEventAdaptorManagerConfigurationException e) {
            OutputEventAdaptorConfiguration outputEventAdaptorConfiguration = new OutputEventAdaptorConfiguration();
            outputEventAdaptorConfiguration.setName(EmailMonitorConstants.EMAIL_OUTPUT_ADAPTER_NAME);
            outputEventAdaptorConfiguration.setType(EmailMonitorConstants.ADAPTER_TYPE_EMAIL);
            try {
                outputEventAdaptorManagerService.deployOutputEventAdaptorConfiguration(outputEventAdaptorConfiguration, axisConfiguration);
            } catch (OutputEventAdaptorManagerConfigurationException ex) {
                logger.error(ex.getMessage());
                throw new EmailMonitorServiceException("Error when adding output adapter", ex);
            }
        }

    }
}

