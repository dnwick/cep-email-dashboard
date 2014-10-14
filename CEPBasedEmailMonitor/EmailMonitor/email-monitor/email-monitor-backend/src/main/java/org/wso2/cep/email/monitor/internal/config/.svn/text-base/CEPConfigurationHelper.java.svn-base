package org.wso2.cep.email.monitor.internal.config;

import org.apache.axis2.engine.AxisConfiguration;
import org.apache.log4j.Logger;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.internal.util.EmailMonitorConstants;

public class CEPConfigurationHelper {
    private static final Logger logger = Logger.getLogger(CEPConfigurationHelper.class);

    StreamDeployer streamDeployer;
    OutputAdapterDeployer outputAdapterDeployer;
    EventFormatterDeployer eventFormatterDeployer;
    RegistryManager registryManager;

    public CEPConfigurationHelper() throws EmailMonitorServiceException {


        streamDeployer = new StreamDeployer();
        outputAdapterDeployer = new OutputAdapterDeployer();
        eventFormatterDeployer = new EventFormatterDeployer();
        registryManager = new RegistryManager();
    }



    public void addCEPConfigurations(String ESBServerIP, String ESBServerPort, String ESBServerUsername, String ESBServerPassword, String mailAddress,int tenantID, AxisConfiguration axisConfiguration) throws EmailMonitorServiceException {

        logger.info("Stared adding CEP configurations");

        streamDeployer.createMailInputStream(tenantID);
        streamDeployer.createFilteredEmailDetailsStream(tenantID);
        streamDeployer.createThreadDetailsStream(tenantID);
        streamDeployer.createLabelDetailsStream(tenantID);
        streamDeployer.createMailOutputStream(tenantID);
        streamDeployer.createEmailSenderOutputStream(tenantID);

        outputAdapterDeployer.createEmailOutputAdapter(axisConfiguration);
        outputAdapterDeployer.createSoapOutputAdapter(axisConfiguration);

        eventFormatterDeployer.createGmailOutStreamEventFormatter(ESBServerIP, ESBServerPort, ESBServerUsername, ESBServerPassword, axisConfiguration);
        eventFormatterDeployer.createEmailSenderOutputStreamFormatter(ESBServerIP, ESBServerPort, ESBServerUsername, ESBServerPassword, axisConfiguration);

        registryManager.addCollection(EmailMonitorConstants.REGISTRY_COLLECTION_NAME);
        registryManager.addCollection(EmailMonitorConstants.REGISTRY_QUERY_COLLECTION_PATH);
        if(!registryManager.resourceAlreadyExists(EmailMonitorConstants.REGISTRY_QUERYCOUNT_RESOURCE_PATH)){

            registryManager.saveResourceString("0", EmailMonitorConstants.REGISTRY_QUERYCOUNT_RESOURCE_PATH);
        }

        logger.info("Successfully completed adding CEP configurations");


    }

    public void removeCEPConfigurations(AxisConfiguration axisConfiguration){
        eventFormatterDeployer.remove(axisConfiguration);
     }
}
