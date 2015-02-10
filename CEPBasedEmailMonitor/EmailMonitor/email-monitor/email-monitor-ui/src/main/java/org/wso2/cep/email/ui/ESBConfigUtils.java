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
package org.wso2.cep.email.ui;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.log4j.Logger;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.stub.admin.EmailMonitorAdminServiceEmailMonitorAdminException;
import org.wso2.cep.email.monitor.stub.admin.EmailMonitorAdminServiceStub;

import java.rmi.RemoteException;

public class ESBConfigUtils {

    private String esbUserName;
    private String esbPassword;
    private String esbIP;
    private String esbPort;
    EmailMonitorAdminServiceStub emailMonitorAdminServiceStub;
    private static final Logger logger = Logger.getLogger(ESBConfigUtils.class);


    public ESBConfigUtils(String esbIP, String esbPort, String esbUserName, String esbPassword){
        this.esbUserName = esbUserName;
        this.esbPassword = esbPassword;
        this.esbIP = esbIP;
        this.esbPort = esbPort;
    }

    public void AddConfigurations(String CEPServerUserName, String CEPServerPassword, String mailUserNAme,String mailAccessToken,String mailClientId,String mailClientSecret,String mailRefreshToken, String CEPServerIP, String CEPServerPort, String backendServerURL, ConfigurationContext configCtx)
            throws EmailMonitorServiceException {
        String endPoint = backendServerURL + "EmailMonitorAdminService";

        try {
            emailMonitorAdminServiceStub = new EmailMonitorAdminServiceStub(configCtx, endPoint);
            emailMonitorAdminServiceStub.addESBConfigurations(esbIP, esbPort, esbUserName, esbPassword, CEPServerUserName, CEPServerPassword, mailUserNAme,mailAccessToken,mailClientId,mailClientSecret,mailRefreshToken, CEPServerIP, CEPServerPort);
        } catch (AxisFault axisFault) {
            logger.error(axisFault.getMessage());
            throw new EmailMonitorServiceException("Error adding configurations", axisFault);
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error adding configurations", e);
        } catch (EmailMonitorAdminServiceEmailMonitorAdminException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error adding configurations", e);
        }


    }


}

