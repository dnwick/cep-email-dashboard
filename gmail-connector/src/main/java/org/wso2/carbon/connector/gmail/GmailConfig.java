/*
 * Copyright (c) 2005-2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.connector.gmail;

import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import com.google.code.javax.mail.MessagingException;

/**
 * Class which reads OAuth access token and user name parameters from the
 * message context to perform OAuth authentication for Gmail.
 */
public class GmailConfig extends AbstractConnector {

    /*
     * Extracts the values for OAuth access token and user name from the message
     * context and stores them in the message context.
     */
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            // Reading OAuth access token and user name from the message context
            String oauthAccessToken =
                    GmailUtils.lookupFunctionParam(messageContext,
                            GmailConstants.GMAIL_PARAM_OAUTH_ACCESS_TOKEN);
            String username =
                    GmailUtils.lookupFunctionParam(messageContext,
                            GmailConstants.GMAIL_PARAM_USERNAME);

            // Reading other OAuth attributes needed to access new Access token
            String oauthRefreshToken = GmailUtils.lookupFunctionParam(messageContext,
                    GmailConstants.GMAIL_PARAM_OAUTH_REFRESH_TOKEN);
            String oauthClientId = GmailUtils.lookupFunctionParam(messageContext,
                    GmailConstants.GMAIL_PARAM_OAUTH_CLIENT_ID);
            String oauthClientSecret = GmailUtils.lookupFunctionParam(messageContext,
                    GmailConstants.GMAIL_PARAM_OAUTH_CLIENT_SECRET);

            // Validating the user name and OAuth access token provided by the
            // user
            if (username == null || "".equals(username.trim()) || oauthAccessToken == null ||
                    "".equals(oauthAccessToken.trim())) {

                String errorLog = "Invalid username or access token";
                log.error(errorLog);
                ConnectException connectException = new ConnectException(errorLog);
                GmailUtils.storeErrorResponseStatus(messageContext,
                        connectException,
                        GmailErrorCodes.GMAIL_ERROR_CODE_CONNECT_EXCEPTION);
                handleException(connectException.getMessage(), connectException, messageContext);
            }

            // Storing OAuth user login details in the message context
            this.storeOauthUserLogin(messageContext, username, oauthAccessToken, oauthRefreshToken, oauthClientId,
                    oauthClientSecret);
        } catch (MessagingException e) {
            GmailUtils.storeErrorResponseStatus(messageContext,
                    e,
                    GmailErrorCodes.GMAIL_ERROR_CODE_MESSAGING_EXCEPTION);
            handleException(e.getMessage(), e, messageContext);
        } catch (Exception e) {
            GmailUtils.storeErrorResponseStatus(messageContext, e,
                    GmailErrorCodes.GMAIL_COMMON_EXCEPTION);
            handleException(e.getMessage(), e, messageContext);
        }
    }

    /**
     * Stores user name and access token for OAuth authentication
     *
     * @param messageContext
     *            message context where the user login information should be
     *            stored
     * @param username
     * @param oauthAccessToken
     * @param oauthRefreshToken
     * @param oauthClientId
     * @param oauthClientSecret
     * @param oauthAccessToken
     *            access token
     * @throws MessagingException
     */
    private void storeOauthUserLogin(MessageContext messageContext, String username,
            String oauthAccessToken, String oauthRefreshToken, String oauthClientId, String oauthClientSecret)
            throws MessagingException, Exception {

        org.apache.axis2.context.MessageContext axis2MessageContext =
                ((Axis2MessageContext) messageContext).getAxis2MessageContext();
        Object loginMode = axis2MessageContext.getProperty(GmailConstants.GMAIL_LOGIN_MODE);

        // Properties messageContext.getProperty (GmailConstants.GMAIL_OAUTH_ACCESS_TOKEN) and messageContext
        // .getProperty GmailConstants.GMAIL_OAUTH_ACCESS_TOKEN) are not going to be null. Hence below null check not
        // necessarily needed.

        if (loginMode != null && (loginMode.toString() == GmailConstants.GMAIL_OAUTH_LOGIN_MODE) &&
                messageContext.getProperty(GmailConstants.GMAIL_OAUTH_USERNAME) != null && messageContext.getProperty
                (GmailConstants.GMAIL_OAUTH_ACCESS_TOKEN) != null && messageContext.getProperty(GmailConstants.
                GMAIL_OAUTH_USERNAME).toString().equals(username) && messageContext.getProperty(GmailConstants
                .GMAIL_OAUTH_ACCESS_TOKEN).toString().equals(oauthAccessToken)) {
            log.info("The same authentication is already available. Hence no changes are needed.");
            return;
        }

        // Reset already stored instances
        GmailUtils.closeConnection(axis2MessageContext);

        log.info("Setting the loggin mode to \"OAUTH\"");
        axis2MessageContext.setProperty(GmailConstants.GMAIL_LOGIN_MODE,
                GmailConstants.GMAIL_OAUTH_LOGIN_MODE);
        log.info("Storing new username,access token,referesh token,client id, client secret ");
        messageContext.setProperty(GmailConstants.GMAIL_OAUTH_USERNAME, username);

        Object prestoredAccessToken =
                axis2MessageContext.getConfigurationContext().getProperty(GmailConstants.GMAIL_OAUTH_ACCESS_TOKEN);

        //Use if there exist newly created accessToken against the username

        if (prestoredAccessToken != null) {

            //Retrieve prestored username
            Object prestoredUsername =
                    axis2MessageContext.getConfigurationContext().getProperty(GmailConstants.GMAIL_OAUTH_USERNAME);

            //if the same user has a access token saved in configuration context then check the validity of the token
            // and add to messageContext
            if (((String) prestoredUsername).equals(username)) {

                //store the access token in message context
                messageContext.setProperty(GmailConstants.GMAIL_OAUTH_ACCESS_TOKEN, (String) prestoredAccessToken);
            }

        } else {
            messageContext.setProperty(GmailConstants.GMAIL_OAUTH_ACCESS_TOKEN, oauthAccessToken);
        }

        messageContext.setProperty(GmailConstants.GMAIL_OAUTH_REFRESH_TOKEN, oauthRefreshToken);
        messageContext.setProperty(GmailConstants.GMAIL_OAUTH_CLIENT_ID, oauthClientId);
        messageContext.setProperty(GmailConstants.GMAIL_OAUTH_CLIENT_SECRET, oauthClientSecret);
    }
}
