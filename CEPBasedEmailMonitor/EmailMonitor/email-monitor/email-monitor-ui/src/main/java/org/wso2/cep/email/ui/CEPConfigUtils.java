package org.wso2.cep.email.ui;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.log4j.Logger;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.stub.admin.EmailMonitorAdminServiceEmailMonitorAdminException;
import org.wso2.cep.email.monitor.stub.admin.EmailMonitorAdminServiceStub;

import java.rmi.RemoteException;

public class CEPConfigUtils {

    EmailMonitorAdminServiceStub emailMonitorAdminServiceStub;
    private static final Logger logger = Logger.getLogger(CEPConfigUtils.class);


    public CEPConfigUtils(String backendServerURL, ConfigurationContext configCtx, String mailquery, String esbIP, String esbPort, String esbUserName, String esbPassword, String mailUserName)
            throws EmailMonitorServiceException {
        String endPoint = backendServerURL + "EmailMonitorAdminService";
        try {
            emailMonitorAdminServiceStub = new EmailMonitorAdminServiceStub(configCtx, endPoint);

           emailMonitorAdminServiceStub.addCEPConfigurations(esbIP, esbPort, esbUserName, esbPassword, mailUserName);

            String[] siddhiQueries = emailMonitorAdminServiceStub.getSiddhiQuery(mailquery);
            emailMonitorAdminServiceStub.createExecutionPlan(siddhiQueries);
            emailMonitorAdminServiceStub.removeCEPConfigurations();
            emailMonitorAdminServiceStub.removeESBConfigurations(esbIP, esbPort);

        } catch (AxisFault axisFault) {
            logger.error(axisFault.getMessage());
            throw new EmailMonitorServiceException("Error adding configurations", axisFault);
        }catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error adding configurations", e);
        } catch (EmailMonitorAdminServiceEmailMonitorAdminException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error adding configurations", e);
        }

    }
}
