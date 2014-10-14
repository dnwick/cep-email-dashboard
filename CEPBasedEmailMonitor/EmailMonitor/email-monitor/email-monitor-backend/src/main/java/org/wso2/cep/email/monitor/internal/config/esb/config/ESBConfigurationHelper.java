package org.wso2.cep.email.monitor.internal.config.esb.config;


import org.apache.log4j.Logger;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.internal.config.esb.config.util.SecurityConstants;

public class ESBConfigurationHelper {
    private static final Logger logger = Logger.getLogger(TaskDeployer.class);
    private ProxyDeployer proxyDeployer;
    private BAMMediatorDeployer bamMediatorDeployer;
    private TaskDeployer taskDeployer;
    private LocalEntryDeployer localEntryDeployer;

    public ESBConfigurationHelper(String ip, String port) throws EmailMonitorServiceException {


        System.setProperty(SecurityConstants.TRUSTSTORE, SecurityConstants.CLIENT_TRUST_STORE_PATH);
        System.setProperty(SecurityConstants.TRUSTSTORE_PASSWORD, SecurityConstants.KEY_STORE_PASSWORD);
        System.setProperty(SecurityConstants.TRUSTSTORE_TYPE, SecurityConstants.KEY_STORE_TYPE);

        localEntryDeployer = new LocalEntryDeployer(ip,port);
        proxyDeployer = new ProxyDeployer(ip, port);
        bamMediatorDeployer = new BAMMediatorDeployer(ip, port);
        taskDeployer = new TaskDeployer(ip, port);


    }



    public void addConfigurations(String ESBUserName, String ESBPassword, String CEPServerUserName, String CEPServerPassword, String mailUserNAme,String mailAccessToken,String mailClientId,String mailClientSecret,String mailRefreshToken,String CEPServerIP ,String CEPServerPort ) throws EmailMonitorServiceException{

        logger.info("Stared adding ESB configurations");

        localEntryDeployer.addLocalEntry(ESBUserName,ESBPassword,mailUserNAme,mailAccessToken,mailClientId,mailClientSecret,mailRefreshToken);
        bamMediatorDeployer.addBAMServerProfile(ESBUserName, ESBPassword, CEPServerUserName, CEPServerPassword , CEPServerIP ,CEPServerPort);
        proxyDeployer.addMailProxy(ESBUserName, ESBPassword);
        proxyDeployer.addLabelAdderProxy(ESBUserName,ESBPassword);
        proxyDeployer.addMailSenderProxy(ESBUserName,ESBPassword);
        taskDeployer.addScheduledTask(ESBUserName, ESBPassword, mailUserNAme,mailAccessToken,mailClientId,mailClientSecret,mailRefreshToken);

    }


    public void removeESBConfigurations() throws EmailMonitorServiceException {
        taskDeployer.removeTask();
        proxyDeployer.removeProxy();
        localEntryDeployer.removeEntries();
        bamMediatorDeployer.removeServerProfile();

    }

}
