/*
*  Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.cep.email.monitor.internal;

import org.apache.axis2.engine.AxisConfiguration;
import org.apache.log4j.Logger;
import org.wso2.cep.email.monitor.EmailMonitorServiceInterface;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.internal.config.*;
import org.wso2.cep.email.monitor.internal.config.esb.config.BAMMediatorDeployer;
import org.wso2.cep.email.monitor.internal.config.esb.config.ESBConfigurationHelper;
import org.wso2.cep.email.monitor.internal.config.esb.config.ProxyDeployer;
import org.wso2.cep.email.monitor.internal.config.esb.config.TaskDeployer;

/**
 * Service class used to deploy services according to users
 * inputs received from front end
 */

public class EmailMonitorService implements EmailMonitorServiceInterface {

    private static Logger logger = Logger.getLogger(EmailMonitorService.class);

    /**
     * Add BAMServerProfile to the ESB registry for send events to BAM or CEP
     * @param ip ip-address of which server runs
     * @param port port number which connects
     * @param userName Email monitor username
     * @param password Email monitor password
     * @param CEPServerUserName Username used to login in CEP server
     * @param CEPServerPassword Password used to login in CEP server
     * @param CEPServerIP IP Address where CEP runs
     * @param CEPServerPort Port number where CEP runs
     * @return
     */
    @Override
    public boolean addBAMServerProfile(String ip, String port, String userName, String password,
            String CEPServerUserName, String CEPServerPassword, String CEPServerIP, String CEPServerPort)
            throws EmailMonitorServiceException {

        try {
            BAMMediatorDeployer bamMediatorDeployer = new BAMMediatorDeployer(ip, port);
            bamMediatorDeployer
                    .addBAMServerProfile(userName, password, CEPServerUserName, CEPServerPassword, CEPServerIP,
                            CEPServerPort);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     * Add MailProxy for ESB
     * @param ip ip-address of which server runs
     * @param port port number which connects
     * @param userName Email monitor username
     * @param password Email monitor password
     * @return
     */
    @Override
    public boolean addMailProxy(String ip, String port, String userName, String password)
            throws EmailMonitorServiceException {

        try {
            ProxyDeployer proxyDeployer = new ProxyDeployer(ip, port);
            proxyDeployer.addMailProxy(userName, password);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     * SchedulingTasks for predically run the ESB proxy
     * @param ip ip-address of which server runs
     * @param port port number which connects
     * @param userName Email monitor username
     * @param password Email monitor password
     * @param mailUserName Mail address userName
     * @param mailAccessToken Oauth Access token
     * @param mailClientId ClientId provided from Oauth provider
     * @param mailClientSecret ClientSecret provided from Oauth provider
     * @param mailRefreshToken Refresh token to retrieve new access token
     * @return
     */
    @Override
    public boolean addScheduledTask(String ip, String port, String userName, String password, String mailUserName,
            String mailAccessToken, String mailClientId, String mailClientSecret, String mailRefreshToken)
            throws EmailMonitorServiceException {

        try {
            TaskDeployer taskDeployer = new TaskDeployer(ip, port);
            taskDeployer
                    .addScheduledTask(userName, password, mailUserName, mailAccessToken, mailClientId, mailClientSecret,
                            mailRefreshToken);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e.getMessage(),e);

        }
    }

    /**
     * Create Execution Plan for deploy in CEP for the run CEP queries among mails
     * @param query user defined query to filter mails
     * @return
     */
    @Override
    public String createExecutionPlan(String[] query, AxisConfiguration axisConfiguration)
            throws EmailMonitorServiceException {

        try {
            ExecutionPlanDeployer executionPlanDeployer = new ExecutionPlanDeployer();
            return executionPlanDeployer.createExecutionPlan(query, axisConfiguration);

        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     * Create MailInputStream and stores it in the CEP
     * @param tenantID id of the tenant
     * @return
     */
    @Override
    public boolean createMailInputStream(int tenantID) throws EmailMonitorServiceException {

        try {
            StreamDeployer streamDeployer = new StreamDeployer();
            streamDeployer.createMailInputStream(tenantID);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     * used to add ESB configurations (Bam proxy and tasks) to configure the ESB using
     * a helper class
     * @param ip ip-address of which server runs
     * @param port port number which connects
     * @param userName Email monitor username
     * @param password Email monitor password
     * @param CEPServerUserName Username used to login in CEP server
     * @param CEPServerPassword Password used to login in CEP server
     * @param mailUserNAme username of the mail account
     * @param mailAccessToken Oauth Access token
     * @param mailClientId ClientId provided from Oauth provider
     * @param mailClientSecret ClientSecret provided from Oauth provider
     * @param mailRefreshToken Refresh token to retrieve new access token
     * @param CEPServerIP IP Address where CEP runs
     * @param CEPServerPort Port number where CEP runs
     */
    public boolean addESBConfigurations(String ip, String port, String userName, String password,
            String CEPServerUserName, String CEPServerPassword, String mailUserNAme, String mailAccessToken,
            String mailClientId, String mailClientSecret, String mailRefreshToken, String CEPServerIP,
            String CEPServerPort) throws EmailMonitorServiceException {
        try {
            ESBConfigurationHelper esbConfigurationHelper = new ESBConfigurationHelper(ip, port);
            esbConfigurationHelper
                    .addConfigurations(userName, password, CEPServerUserName, CEPServerPassword, mailUserNAme,
                            mailAccessToken, mailClientId, mailClientSecret, mailRefreshToken, CEPServerIP,
                            CEPServerPort);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e.getMessage(),e);

        }
    }

    /**
     * used to add CEP configurations to configure the ESB using
     * a helper class
     * @param ESBServerIP IP address which ESB runs
     * @param ESBServerPort port which ESB runs
     * @param ESBServerUsername Username used to login in ESB server
     * @param ESBServerPassword Password used to login in ESB server
     * @param mailAddress email address
     * @param tenantID id of the tenant
     * @param axisConfiguration
     */
    public boolean addCEPConfigurations(String ESBServerIP, String ESBServerPort, String ESBServerUsername,
            String ESBServerPassword, String mailAddress, int tenantID, AxisConfiguration axisConfiguration)
            throws EmailMonitorServiceException {
        try {
            CEPConfigurationHelper cepConfigurationHelper = new CEPConfigurationHelper();
            cepConfigurationHelper
                    .addCEPConfigurations(ESBServerIP, ESBServerPort, ESBServerUsername, ESBServerPassword, mailAddress,
                            tenantID, axisConfiguration);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e.getMessage(),e);

        }
    }

    /**
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean createMailOutputStream(int tenantID) throws EmailMonitorServiceException {
        try {
            StreamDeployer streamDeployer = new StreamDeployer();
            streamDeployer.createMailOutputStream(tenantID);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     *
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean createThreadDetailsStream(int tenantID) throws EmailMonitorServiceException {
        try {
            StreamDeployer streamDeployer = new StreamDeployer();
            streamDeployer.createThreadDetailsStream(tenantID);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     *
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean createLabelDetailsStream(int tenantID) throws EmailMonitorServiceException {
        try {
            StreamDeployer streamDeployer = new StreamDeployer();
            streamDeployer.createLabelDetailsStream(tenantID);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     *
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean createEmailSenderOutputStream(int tenantID) throws EmailMonitorServiceException {
        try {
            StreamDeployer streamDeployer = new StreamDeployer();
            streamDeployer.createEmailSenderOutputStream(tenantID);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     *
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean createFilteredEmailDetailsStream(int tenantID) throws EmailMonitorServiceException {
        try {
            StreamDeployer streamDeployer = new StreamDeployer();
            streamDeployer.createFilteredEmailDetailsStream(tenantID);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     * @param axisConfiguration
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean createSoapOutputAdapter(AxisConfiguration axisConfiguration) throws EmailMonitorServiceException {
        try {
            OutputAdapterDeployer outputAdapterDeployer = new OutputAdapterDeployer();
            outputAdapterDeployer.createSoapOutputAdapter(axisConfiguration);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     *
     * @param axisConfiguration
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean createEmailOutputAdapter(AxisConfiguration axisConfiguration) throws EmailMonitorServiceException {
        try {
            OutputAdapterDeployer outputAdapterDeployer = new OutputAdapterDeployer();
            outputAdapterDeployer.createEmailOutputAdapter(axisConfiguration);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     *
     * @param ESBServerIP IP address which ESB runs
     * @param ESBServerPort port which ESB runs
     * @param ESBServerUsername Username used to login in ESB server
     * @param ESBServerPassword Password used to login in ESB server
     * @param axisConfiguration configurations to create formatter
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean createGmailOutStreamEventFormatter(String ESBServerIP, String ESBServerPort,
            String ESBServerUsername, String ESBServerPassword, AxisConfiguration axisConfiguration)
            throws EmailMonitorServiceException {
        try {
            EventFormatterDeployer eventFormatterDeployer = new EventFormatterDeployer();
            eventFormatterDeployer.createGmailOutStreamEventFormatter(ESBServerIP, ESBServerPort, ESBServerUsername,
                    ESBServerPassword, axisConfiguration);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     *
     * @param ESBServerIP IP address which ESB runs
     * @param ESBServerPort port which ESB runs
     * @param ESBServerUsername Username used to login in ESB server
     * @param ESBServerPassword Password used to login in ESB server
     * @param axisConfiguration configurations to create formatter
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean createEmailSenderOutputStreamFormatter(String ESBServerIP, String ESBServerPort,
            String ESBServerUsername, String ESBServerPassword, AxisConfiguration axisConfiguration)
            throws EmailMonitorServiceException {
        try {
            EventFormatterDeployer eventFormatterDeployer = new EventFormatterDeployer();
            eventFormatterDeployer.createEmailSenderOutputStreamFormatter(ESBServerIP, ESBServerPort, ESBServerUsername,
                    ESBServerPassword, axisConfiguration);
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     *
     * @param resourceString resource to be saved
     * @param resourcePath resource path
     * @return
     */
    @Override
    public boolean saveResourceString(String resourceString, String resourcePath) {
        RegistryManager registryManager = new RegistryManager();
        registryManager.saveResourceString(resourceString, resourcePath);
        return true;
    }

    /**
     *
     * @param path resource path
     * @return
     */
    @Override
    public boolean resourceAlreadyExists(String path) {
        RegistryManager registryManager = new RegistryManager();
        registryManager.resourceAlreadyExists(path);
        return true;
    }

    /**
     *
     * @param path resource path
     * @return
     */
    @Override
    public boolean removeResource(String path) {
        RegistryManager registryManager = new RegistryManager();
        registryManager.removeResource(path);
        return true;
    }

    /**
     *
     * @param path resource path
     * @return
     */
    @Override
    public String getResourceString(String path) {
        RegistryManager registryManager = new RegistryManager();
        return registryManager.getResourceString(path);
    }

    /**
     *
     * @param collectionPath collection path
     * @return
     */
    @Override
    public boolean addCollection(String collectionPath) {
        RegistryManager registryManager = new RegistryManager();
        registryManager.addCollection(collectionPath);
        return true;
    }

    /**
     *
     * @param emailMonitorCollectionLocation
     * @return
     */
    @Override
    public String[] getEmailMonitorResources(String emailMonitorCollectionLocation) {
        RegistryManager registryManager = new RegistryManager();
        return registryManager.getEmailMonitorResources(emailMonitorCollectionLocation);
    }

    /**
     *
     * @param ip IP address which ESB runs
     * @param port port which ESB runs
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean removeESBConfigurations(String ip, String port) throws EmailMonitorServiceException {
        try {
            ESBConfigurationHelper esbConfigurationHelper = new ESBConfigurationHelper(ip, port);
            esbConfigurationHelper.removeESBConfigurations();
            return true;
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);

        }
    }

    /**
     *
     * @param axisConfiguration
     * @return
     * @throws EmailMonitorServiceException
     */
    @Override
    public boolean removeCEPConfigurations(AxisConfiguration axisConfiguration) throws EmailMonitorServiceException {

        CEPConfigurationHelper cepConfigurationHelper = null;
        try {
            cepConfigurationHelper = new CEPConfigurationHelper();
        } catch (EmailMonitorServiceException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException(e);
        }
        cepConfigurationHelper.removeCEPConfigurations(axisConfiguration);
        return true;

    }

}
