package org.wso2.cep.email.monitor.internal.config.esb.config;


import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.wso2.carbon.localentry.stub.types.LocalEntryAdminException;
import org.wso2.carbon.localentry.stub.types.LocalEntryAdminServiceStub;
import org.wso2.carbon.utils.CarbonUtils;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.internal.util.EmailMonitorConstants;

import java.rmi.RemoteException;

public class LocalEntryDeployer {

    private static final Logger logger = Logger.getLogger(LocalEntryDeployer.class);
    private LocalEntryAdminServiceStub stub;


    public LocalEntryDeployer(String ip, String port) throws EmailMonitorServiceException {
        String endPoint = EmailMonitorConstants.PROTOCOL + ip + ":" + port + EmailMonitorConstants.SERVICES + EmailMonitorConstants.LOCAL_ENTRY_ADMIN_SERVICE;

        try {
            stub = new LocalEntryAdminServiceStub(endPoint);
        } catch (AxisFault axisFault) {
            logger.error(axisFault.getMessage());
            throw new EmailMonitorServiceException("Error when creating LocalEntryDeployer", axisFault);
        }


    }

    public void addLocalEntry(String userName, String password, String mailUserNAme,String mailAccessToken,String mailClientId,String mailClientSecret,String mailRefreshToken) throws EmailMonitorServiceException {
        CarbonUtils.setBasicAccessSecurityHeaders(userName, password, stub._getServiceClient());

        boolean isEmailEntryExist;
        boolean isPasswordEntryExist;
        boolean isAccessTokenEntryExist;
        boolean isClientIdEntryExist;
        boolean isClientSecretEntryExist;
        boolean isRefreshTokenEntryExist;
        try {
            String entrySet = stub.getEntryNamesString();
            isEmailEntryExist = entrySet.contains("[Entry]-email");
            isPasswordEntryExist = entrySet.contains("[Entry]-password");
            isAccessTokenEntryExist = entrySet.contains("[Entry]-accessToken");
            isClientIdEntryExist = entrySet.contains("[Entry]-clientId");
            isClientSecretEntryExist = entrySet.contains("[Entry]-clientSecret");
            isRefreshTokenEntryExist = entrySet.contains("[Entry]-refreshToken");
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding local entry", e);
        } catch (LocalEntryAdminException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding local entry", e);
        }

        CryptographyManager cryptographyManager = new CryptographyManager();

        try {

            if(isEmailEntryExist) {
                stub.deleteEntry("email");
            }
            if(isPasswordEntryExist) {
                stub.deleteEntry("password");
            }
            if(isAccessTokenEntryExist) {
                stub.deleteEntry("accessToken");
            }
            if(isClientIdEntryExist) {
                stub.deleteEntry("clientId");
            }
            if(isClientSecretEntryExist) {
                stub.deleteEntry("clientSecret");
            }
            if(isRefreshTokenEntryExist) {
                stub.deleteEntry("refreshToken");
            }
            stub.addEntry("<localEntry key=\"email\"><email>" + mailUserNAme + "</email><description/></localEntry>");
            stub.addEntry("<localEntry key=\"accessToken\"><accessToken>" + mailAccessToken + "</accessToken><description/></localEntry>");
            stub.addEntry("<localEntry key=\"clientId\"><clientId>" + mailClientId + "</clientId><description/></localEntry>");
            stub.addEntry("<localEntry key=\"clientSecret\"><clientSecret>" + mailClientSecret + "</clientSecret><description/></localEntry>");
            stub.addEntry("<localEntry key=\"refreshToken\"><refreshToken>" + mailUserNAme + "</refreshToken><description/></localEntry>");
            
            //stub.addEntry("<localEntry key=\"password\"><password>" + cryptographyManager.encryptAndBase64Encode(mailPassword) + "</password><description/></localEntry>");

        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when deleting local entry", e);
        } catch (LocalEntryAdminException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when deleting local entry", e);
        }
    }


    public void removeEntries() throws EmailMonitorServiceException {

        boolean isEmailEntryExist;
        boolean isPasswordEntryExist;
        boolean isAccessTokenEntryExist;
        boolean isClientIdEntryExist;
        boolean isClientSecretEntryExist;
        boolean isRefreshTokenEntryExist;
        try {
            String entrySet = stub.getEntryNamesString();
            isEmailEntryExist = entrySet.contains("[Entry]-email");
            isPasswordEntryExist = entrySet.contains("[Entry]-password");
            isAccessTokenEntryExist= entrySet.contains("[Entry]-accessToken");
            isClientIdEntryExist= entrySet.contains("[Entry]-clientId");
            isClientSecretEntryExist= entrySet.contains("[Entry]-clientSecret");
            isRefreshTokenEntryExist= entrySet.contains("[Entry]-refreshToken");
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when getting local entry", e);
        } catch (LocalEntryAdminException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when getting local entry", e);
        }

         try {

            if(isEmailEntryExist) {
                stub.deleteEntry("email");
            }
            if(isPasswordEntryExist) {
                stub.deleteEntry("password");
            }
            if(isAccessTokenEntryExist) {
                stub.deleteEntry("accessToken");
            }
            if(isClientIdEntryExist) {
                stub.deleteEntry("clientId");
            }
            if(isClientSecretEntryExist) {
                stub.deleteEntry("clientSecret");
            } 
            if(isRefreshTokenEntryExist) {
                stub.deleteEntry("refreshToken");
            }
        } catch (RemoteException e) {
             logger.error(e.getMessage());
             throw new EmailMonitorServiceException("Error when deleting local entry", e);
        } catch (LocalEntryAdminException e) {
             logger.error(e.getMessage());
             throw new EmailMonitorServiceException("Error when deleting local entry", e);
        }

    }


    }