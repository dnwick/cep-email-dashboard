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
     *
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
     *
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
     * @param query
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

    @Override
    public boolean saveResourceString(String resourceString, String resourcePath) {
        RegistryManager registryManager = new RegistryManager();
        registryManager.saveResourceString(resourceString, resourcePath);
        return true;
    }

    @Override
    public boolean resourceAlreadyExists(String path) {
        RegistryManager registryManager = new RegistryManager();
        registryManager.resourceAlreadyExists(path);
        return true;
    }

    @Override
    public boolean removeResource(String path) {
        RegistryManager registryManager = new RegistryManager();
        registryManager.removeResource(path);
        return true;
    }

    @Override
    public String getResourceString(String path) {
        RegistryManager registryManager = new RegistryManager();
        return registryManager.getResourceString(path);
    }

    @Override
    public boolean addCollection(String collectionPath) {
        RegistryManager registryManager = new RegistryManager();
        registryManager.addCollection(collectionPath);
        return true;
    }

    @Override
    public String[] getEmailMonitorResources(String emailMonitorCollectionLocation) {
        RegistryManager registryManager = new RegistryManager();
        return registryManager.getEmailMonitorResources(emailMonitorCollectionLocation);
    }

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
