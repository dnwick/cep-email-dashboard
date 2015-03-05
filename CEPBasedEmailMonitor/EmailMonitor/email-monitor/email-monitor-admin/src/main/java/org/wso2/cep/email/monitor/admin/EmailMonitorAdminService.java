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
package org.wso2.cep.email.monitor.admin;

import org.apache.log4j.Logger;
import org.wso2.carbon.core.AbstractAdmin;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.cep.email.monitor.EmailMonitorServiceInterface;
import org.wso2.cep.email.monitor.admin.exception.EmailMonitorAdminException;
import org.wso2.cep.email.monitor.admin.internal.EmailMonitorAdminValueHolder;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.query.compiler.QueryManagerServiceInterface;
import org.wso2.cep.email.monitor.query.compiler.exeception.EmailMonitorCompilerException;

public class EmailMonitorAdminService extends AbstractAdmin {

    private static final Logger log = Logger.getLogger(EmailMonitorAdminService.class);

    /**
     * Add BAMServerProfile to the ESB registry for send events to BAM or CEP
     *
     * @param ip
     * @param port
     * @param userName
     * @param password
     * @param CEPServerUserName
     * @param CEPServerPassword
     * @param CEPServerIP
     * @param CEPServerPort
     * @throws EmailMonitorAdminException
     * @return
     */
    public boolean addBAMServerProfile(String ip, String port, String userName, String password,
            String CEPServerUserName, String CEPServerPassword, String CEPServerIP, String CEPServerPort)
            throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface
                    .addBAMServerProfile(ip, port, userName, password, CEPServerUserName, CEPServerPassword,
                            CEPServerIP, CEPServerPort);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     * Add MailProxy for ESB
     *
     * @param ip
     * @param port
     * @param userName
     * @param password
     * @throws EmailMonitorAdminException
     * @return
     */
    public boolean addMailProxy(String ip, String port, String userName, String password)
            throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.addMailProxy(ip, port, userName, password);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     * SchedulingTasks for predically run the ESB proxy
     * @param ip
     * @param port
     * @param userName
     * @param password
     * @param mailUserName
     * @param mailAccessToken
     * @param mailClientId
     * @param mailClientSecret
     * @param mailRefreshToken
     * @throws EmailMonitorAdminException
     * @return
     */
    public boolean addScheduledTask(String ip, String port, String userName, String password, String mailUserName,
            String mailAccessToken, String mailClientId, String mailClientSecret, String mailRefreshToken)
            throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface
                    .addScheduledTask(ip, port, userName, password, mailUserName, mailAccessToken, mailClientId,
                            mailClientSecret, mailRefreshToken);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e.getMessage(),e);
        }
    }

    /**
     * Returns the siddhi query
     * @param query
     * @throws EmailMonitorAdminException
     * @return
     */
    public String[] getSiddhiQuery(String query) throws EmailMonitorAdminException {
        QueryManagerServiceInterface queryManagerServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getQueryManagerServiceInterface();
        try {
            return queryManagerServiceInterface.getSiddhiQuery(query);
        } catch (EmailMonitorCompilerException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }

    }

    /**
     * Create Execution Plan for deploy in CEP for the run CEP queries among mails
     * @param query
     * @throws EmailMonitorAdminException
     * @return
     */
    public String createExecutionPlan(String[] query) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.createExecutionPlan(query, getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     * Create MailInputStream and stores it in the CEP
     * @throws EmailMonitorAdminException
     * @return
     */
    public boolean createMailInputStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();

        int tenantID = 0;
        try {
            tenantID = getUserRealm().getRealmConfiguration().getTenantId();
        } catch (UserStoreException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
        try {
            return emailMonitorServiceInterface.createMailInputStream(tenantID);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean createMailOutputStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();

        int tenantID = 0;
        try {
            tenantID = getUserRealm().getRealmConfiguration().getTenantId();
        } catch (UserStoreException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
        try {
            return emailMonitorServiceInterface.createMailOutputStream(tenantID);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean createThreadDetailsStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();

        int tenantID = 0;
        try {
            tenantID = getUserRealm().getRealmConfiguration().getTenantId();
        } catch (UserStoreException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
        try {
            return emailMonitorServiceInterface.createThreadDetailsStream(tenantID);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean createLabelDetailsStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();

        int tenantID = 0;
        try {
            tenantID = getUserRealm().getRealmConfiguration().getTenantId();
        } catch (UserStoreException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
        try {
            return emailMonitorServiceInterface.createLabelDetailsStream(tenantID);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean createEmailSenderOutputStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();

        int tenantID = 0;
        try {
            tenantID = getUserRealm().getRealmConfiguration().getTenantId();
        } catch (UserStoreException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
        try {
            return emailMonitorServiceInterface.createEmailSenderOutputStream(tenantID);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean createFilteredEmailDetailsStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();

        int tenantID = 0;
        try {
            tenantID = getUserRealm().getRealmConfiguration().getTenantId();
        } catch (UserStoreException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
        try {
            return emailMonitorServiceInterface.createFilteredEmailDetailsStream(tenantID);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean createSoapOutputAdapter() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.createSoapOutputAdapter(getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean createEmailOutputAdapter() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.createEmailOutputAdapter(getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean createEventFormatter(String ESBServerIP, String ESBServerPort, String ESBServerUsername,
            String ESBServerPassword, String eventFormatterConfigurationXML) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface
                    .createGmailOutStreamEventFormatter(ESBServerIP, ESBServerPort, ESBServerUsername,
                            ESBServerPassword, getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @param ESBServerIP
     * @param ESBServerPort
     * @param ESBServerUsername
     * @param ESBServerPassword
     * @param eventFormatterConfigurationXML
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean createEmailSenderOutputStreamFormatter(String ESBServerIP, String ESBServerPort,
            String ESBServerUsername, String ESBServerPassword, String eventFormatterConfigurationXML)
            throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface
                    .createEmailSenderOutputStreamFormatter(ESBServerIP, ESBServerPort, ESBServerUsername,
                            ESBServerPassword, getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     * used to add ESB configurations (Bam proxy and tasks) to configure the ESB using
     * a helper class
     * @param ip
     * @param port
     * @param userName
     * @param password
     * @param CEPServerUserName
     * @param CEPServerPassword
     * @param mailUserNAme
     * @param mailAccessToken
     * @param mailClientId
     * @param mailClientSecret
     * @param mailRefreshToken
     * @param CEPServerIP
     * @param CEPServerPort
     */
    public boolean addESBConfigurations(String ip, String port, String userName, String password,
            String CEPServerUserName, String CEPServerPassword, String mailUserNAme, String mailAccessToken,
            String mailClientId, String mailClientSecret, String mailRefreshToken, String CEPServerIP,
            String CEPServerPort) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface
                    .addESBConfigurations(ip, port, userName, password, CEPServerUserName, CEPServerPassword,
                            mailUserNAme, mailAccessToken, mailClientId, mailClientSecret, mailRefreshToken,
                            CEPServerIP, CEPServerPort);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e.getMessage(),e);
        }
    }

    /**
     *
     * @param ip
     * @param port
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean removeESBConfigurations(String ip, String port) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.removeESBConfigurations(ip, port);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     * used to add CEP configurations to configure the ESB using
     * a helper class
     * @param ESBServerIP
     * @param ESBServerPort
     * @param ESBServerUsername
     * @param ESBServerPassword
     * @param mailAddress
     * @throws EmailMonitorServiceException
     */
    public boolean addCEPConfigurations(String ESBServerIP, String ESBServerPort, String ESBServerUsername,
            String ESBServerPassword, String mailAddress) throws EmailMonitorAdminException {

        int tenantID = 0;
        try {
            tenantID = getUserRealm().getRealmConfiguration().getTenantId();
        } catch (UserStoreException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e.getMessage(),e);
        }

        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface
                    .addCEPConfigurations(ESBServerIP, ESBServerPort, ESBServerUsername, ESBServerPassword, mailAddress,
                            tenantID, getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @return
     * @throws EmailMonitorAdminException
     */
    public boolean removeCEPConfigurations() throws EmailMonitorAdminException {

        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.removeCEPConfigurations(getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    /**
     *
     * @param resourceString
     * @param resourcePath
     * @return
     */
    public boolean saveResourceString(String resourceString, String resourcePath) {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        return emailMonitorServiceInterface.saveResourceString(resourceString, resourcePath);
    }

    /**
     *
     * @param path
     * @return
     */
    public boolean resourceAlreadyExists(String path) {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        return emailMonitorServiceInterface.resourceAlreadyExists(path);
    }

    /**
     *
     * @param path
     * @return
     */
    public boolean removeResource(String path) {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        return emailMonitorServiceInterface.removeResource(path);
    }

    /**
     *
     * @param path
     * @return
     */
    public String getResourceString(String path) {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        return emailMonitorServiceInterface.getResourceString(path);
    }

    /**
     *
     * @param collectionPath
     * @return
     */
    public boolean addCollection(String collectionPath) {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        return emailMonitorServiceInterface.addCollection(collectionPath);
    }

    /**
     *
     * @param emailMonitorCollectionLocation
     * @return
     */
    public String[] getEmailMonitorResources(String emailMonitorCollectionLocation) {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance()
                .getEmailMonitorService();
        return emailMonitorServiceInterface.getEmailMonitorResources(emailMonitorCollectionLocation);
    }

}
