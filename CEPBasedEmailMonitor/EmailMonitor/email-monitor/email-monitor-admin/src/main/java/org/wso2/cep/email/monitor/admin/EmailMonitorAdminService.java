/*
        ~ Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
        ~
        ~ WSO2 Inc. licenses this file to you under the Apache License,
        ~ Version 2.0 (the "License"); you may not use this file except
        ~ in compliance with the License.
        ~ You may obtain a copy of the License at
        ~
        ~    http://www.apache.org/licenses/LICENSE-2.0
        ~
        ~ Unless required by applicable law or agreed to in writing,
        ~ software distributed under the License is distributed on an
        ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
        ~ KIND, either express or implied.  See the License for the
        ~ specific language governing permissions and limitations
        ~ under the License.*/
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


    public boolean addBAMServerProfile(String ip, String port, String userName, String password, String CEPServerUserName, String CEPServerPassword, String CEPServerIP, String CEPServerPort) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.addBAMServerProfile(ip, port, userName, password, CEPServerUserName, CEPServerPassword, CEPServerIP, CEPServerPort);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }


    public boolean addMailProxy(String ip, String port, String userName, String password) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.addMailProxy(ip, port, userName, password);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }


    public boolean addScheduledTask(String ip, String port, String userName, String password, String mailUserName, String mailAccessToken,String mailClientId,String mailClientSecret,String mailRefreshToken) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
        	return emailMonitorServiceInterface.addScheduledTask(ip, port, userName, password, mailUserName,mailAccessToken,mailClientId,mailClientSecret,mailRefreshToken);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    public String[] getSiddhiQuery(String query) throws EmailMonitorAdminException {
        QueryManagerServiceInterface queryManagerServiceInterface = EmailMonitorAdminValueHolder.getInstance().getQueryManagerServiceInterface();
        try {
          return   queryManagerServiceInterface.getSiddhiQuery(query);
        } catch (EmailMonitorCompilerException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }


    }




    public String createExecutionPlan(String[] query) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.createExecutionPlan(query, getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    public boolean createMailInputStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();

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

    public boolean createMailOutputStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();

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

    public boolean createThreadDetailsStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();

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

    public boolean createLabelDetailsStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();

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

    public boolean createEmailSenderOutputStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();

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

    public boolean createFilteredEmailDetailsStream() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();

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


    public boolean createSoapOutputAdapter() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.createSoapOutputAdapter(getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    public boolean createEmailOutputAdapter() throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.createEmailOutputAdapter(getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    public boolean createEventFormatter(String ESBServerIP, String ESBServerPort, String ESBServerUsername, String ESBServerPassword, String eventFormatterConfigurationXML) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.createGmailOutStreamEventFormatter(ESBServerIP,ESBServerPort,ESBServerUsername,ESBServerPassword, getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    public boolean createEmailSenderOutputStreamFormatter(String ESBServerIP, String ESBServerPort, String ESBServerUsername, String ESBServerPassword, String eventFormatterConfigurationXML) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.createEmailSenderOutputStreamFormatter(ESBServerIP,ESBServerPort,ESBServerUsername,ESBServerPassword, getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }


    public boolean addESBConfigurations(String ip, String port, String userName, String password, String CEPServerUserName, String CEPServerPassword, String mailUserNAme,String mailAccessToken,String mailClientId,String mailClientSecret,String mailRefreshToken,String CEPServerIP, String CEPServerPort) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
        	return emailMonitorServiceInterface.addESBConfigurations(ip, port, userName, password, CEPServerUserName, CEPServerPassword, mailUserNAme,mailAccessToken,mailClientId,mailClientSecret,mailRefreshToken,CEPServerIP, CEPServerPort);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    public boolean removeESBConfigurations(String ip, String port) throws EmailMonitorAdminException {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.removeESBConfigurations(ip, port);
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    public boolean addCEPConfigurations(String ESBServerIP, String ESBServerPort, String ESBServerUsername, String ESBServerPassword, String mailAddress) throws EmailMonitorAdminException {

        int tenantID = 0;
        try {
            tenantID = getUserRealm().getRealmConfiguration().getTenantId();
        } catch (UserStoreException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }


        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.addCEPConfigurations(ESBServerIP, ESBServerPort, ESBServerUsername, ESBServerPassword, mailAddress, tenantID, getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }

    public boolean removeCEPConfigurations() throws EmailMonitorAdminException {

       EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        try {
            return emailMonitorServiceInterface.removeCEPConfigurations( getAxisConfig());
        } catch (EmailMonitorServiceException e) {
            log.error(e.getMessage());
            throw new EmailMonitorAdminException(e);
        }
    }


    public boolean saveResourceString(String resourceString, String resourcePath){
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        return emailMonitorServiceInterface.saveResourceString(resourceString, resourcePath);
    }

    public boolean resourceAlreadyExists(String path){
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        return emailMonitorServiceInterface.resourceAlreadyExists(path);
    }

    public boolean removeResource(String path) {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        return emailMonitorServiceInterface.removeResource(path);
    }

    public String getResourceString(String path){
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        return emailMonitorServiceInterface.getResourceString(path);
    }

    public boolean addCollection(String collectionPath){
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        return emailMonitorServiceInterface.addCollection(collectionPath);
    }

    public String[] getEmailMonitorResources(String emailMonitorCollectionLocation) {
        EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorAdminValueHolder.getInstance().getEmailMonitorService();
        return emailMonitorServiceInterface.getEmailMonitorResources(emailMonitorCollectionLocation);
    }

    }
