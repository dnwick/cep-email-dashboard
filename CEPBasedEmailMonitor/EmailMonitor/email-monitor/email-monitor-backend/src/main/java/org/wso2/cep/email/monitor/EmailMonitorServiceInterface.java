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
package org.wso2.cep.email.monitor;

import org.apache.axis2.engine.AxisConfiguration;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;

/**
 * This is the EmailMonitorServiceInterface for access EmailMonitorService functinalites from service consumer.
 */
public interface EmailMonitorServiceInterface {

    /**
     * Add BAMServerProfile to the ESB registry for send events to BAM or CEP
     *
     * @param ip ip-address of which server runs
     * @param port port number which connects
     * @param userName Email monitor username
     * @param password Email monitor password
     * @param CEPServerUserName
     * @param CEPServerPassword
     * @param CEPServerIP ip address which CEP runs
     * @param CEPServerPort port which CEP runs
     * @return
     */
    public boolean addBAMServerProfile(String ip, String port, String userName, String password,
            String CEPServerUserName, String CEPServerPassword, String CEPServerIP, String CEPServerPort)
            throws EmailMonitorServiceException;

    /**
     * Add MailProxy for ESB
     *
     * @param ip ip-address of which server runs
     * @param port port number which connects
     * @param userName Email monitor username
     * @param password Email monitor password
     * @return
     */
    public boolean addMailProxy(String ip, String port, String userName, String password)
            throws EmailMonitorServiceException;

    /**
     * SchedulingTasks for predically run the ESB proxy
     *
     * @param ip ip-address of which server runs
     * @param port port number which connects
     * @param userName Email monitor username
     * @param password Email monitor password
     * @param mailUserName
     * @param mailAccessToken
     * @param mailClientId
     * @param mailClientSecret
     * @param mailRefreshToken
     * @return
     */
    public boolean addScheduledTask(String ip, String port, String userName, String password, String mailUserName,
            String mailAccessToken, String mailClientId, String mailClientSecret, String mailRefreshToken)
            throws EmailMonitorServiceException;

    /**
     * Create Execution Plan for deploy in CEP for the run CEP queries among mails
     * @param query
     * @return
     */
    public String createExecutionPlan(String[] query, AxisConfiguration axisConfiguration)
            throws EmailMonitorServiceException;

    /**
     * Create MailInputStream and stores it in the CEP
     * @return
     */
    public boolean createMailInputStream(int tenantID) throws EmailMonitorServiceException;

    /**
     * Add bam proxy and tasks configurations to esb
     *
     * @param ip ip-address of which server runs
     * @param port port number which connects
     * @param userName Email monitor username
     * @param password Email monitor password
     * @param CEPServerUserName
     * @param CEPServerPassword
     * @param mailUserNAme
     * @param mailAccessToken Oauth Access token
     * @param mailClientId ClientId provided from Oauth provider
     * @param mailClientSecret ClientSecret provided from Oauth provider
     * @param mailRefreshToken Refresh token to retrieve new access token
     * @param CEPServerIP
     * @param CEPServerPort
     * @return
     */
    public boolean addESBConfigurations(String ip, String port, String userName, String password,
            String CEPServerUserName, String CEPServerPassword, String mailUserNAme, String mailAccessToken,
            String mailClientId, String mailClientSecret, String mailRefreshToken, String CEPServerIP,
            String CEPServerPort) throws EmailMonitorServiceException;

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
            throws EmailMonitorServiceException;

    /**
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean createMailOutputStream(int tenantID) throws EmailMonitorServiceException;

    /**
     *
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean createThreadDetailsStream(int tenantID) throws EmailMonitorServiceException;

    /**
     *
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean createLabelDetailsStream(int tenantID) throws EmailMonitorServiceException;

    /**
     *
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean createEmailSenderOutputStream(int tenantID) throws EmailMonitorServiceException;

    /**
     *
     * @param tenantID id of the tenant
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean createFilteredEmailDetailsStream(int tenantID) throws EmailMonitorServiceException;

    /**
     * @param axisConfiguration
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean createSoapOutputAdapter(AxisConfiguration axisConfiguration) throws EmailMonitorServiceException;

    /**
     *
     * @param axisConfiguration
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean createEmailOutputAdapter(AxisConfiguration axisConfiguration) throws EmailMonitorServiceException;

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

    public boolean createGmailOutStreamEventFormatter(String ESBServerIP, String ESBServerPort,
            String ESBServerUsername, String ESBServerPassword, AxisConfiguration axisConfiguration)
            throws EmailMonitorServiceException;

    ;

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
    public boolean createEmailSenderOutputStreamFormatter(String ESBServerIP, String ESBServerPort,
            String ESBServerUsername, String ESBServerPassword, AxisConfiguration axisConfiguration)
            throws EmailMonitorServiceException;

    ;

    /**
     *
     * @param resourceString resource to be saved
     * @param resourcePath resource path
     * @return
     */
    public boolean saveResourceString(String resourceString, String resourcePath);

    /**
     *
     * @param path resource path
     * @return
     */
    public boolean resourceAlreadyExists(String path);

    /**
     *
     * @param path resource path
     * @return
     */
    public boolean removeResource(String path);

    /**
     *
     * @param path resource path
     * @return
     */
    public String getResourceString(String path);

    /**
     *
     * @param collectionPath collection path
     * @return
     */
    public boolean addCollection(String collectionPath);

    /**
     *
     * @param emailMonitorCollectionLocation
     * @return
     */
    public String[] getEmailMonitorResources(String emailMonitorCollectionLocation);

    /**
     *
     * @param ip IP address which ESB runs
     * @param port port which ESB runs
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean removeESBConfigurations(String ip, String port) throws EmailMonitorServiceException;

    /**
     *
     * @param axisConfiguration
     * @return
     * @throws EmailMonitorServiceException
     */
    public boolean removeCEPConfigurations(AxisConfiguration axisConfiguration) throws EmailMonitorServiceException;

}






