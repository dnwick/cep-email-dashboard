package org.wso2.cep.email.monitor.internal.config.esb.config;


import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.wso2.carbon.task.stub.TaskAdminStub;
import org.wso2.carbon.task.stub.TaskManagementException;
import org.wso2.carbon.utils.CarbonUtils;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.internal.util.EmailMonitorConstants;

import javax.xml.stream.XMLStreamException;
import java.rmi.RemoteException;

public class TaskDeployer {

    private static final Logger logger = Logger.getLogger(TaskDeployer.class);
    private TaskAdminStub stub;
    private XMLReader xmlReader;


    public TaskDeployer(String ip, String port) throws EmailMonitorServiceException {
        xmlReader = new XMLReader();
        String endPoint = EmailMonitorConstants.PROTOCOL + ip + ":" + port + EmailMonitorConstants.SERVICES + EmailMonitorConstants.TASK_ADMIN_SERVICE;

        try {
            stub = new TaskAdminStub(endPoint);
        } catch (AxisFault axisFault) {
            logger.error(axisFault.getMessage());
            throw new EmailMonitorServiceException("Error when creating Task Deployer", axisFault);
        }


    }

    public void addScheduledTask(String userName, String password, String mailUserName, String mailAccessToken,String mailClientId,String mailClientSecret,String mailRefreshToken) throws EmailMonitorServiceException {
        CarbonUtils.setBasicAccessSecurityHeaders(userName, password, stub._getServiceClient());
        boolean isTaskExist = false;
        try {
            isTaskExist = stub.isContains(EmailMonitorConstants.TASK_NAME, EmailMonitorConstants.TASK_GROUP);
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when searching for task 'getmail' ", e);
        } catch (TaskManagementException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when searching for task 'getmail' ", e);
        }


        if(isTaskExist) {
            try {
                stub.deleteTaskDescription(EmailMonitorConstants.TASK_NAME,EmailMonitorConstants.TASK_GROUP);
            } catch (RemoteException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when deleting Tasks ", e);
            } catch (TaskManagementException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when deleting Tasks ", e);
            }

        }
            String content = xmlReader.readXML(EmailMonitorConstants.TASK_CONFIGURATION_FILE_PATH);
            

            content = content.replace(EmailMonitorConstants.GMAIL_USERNAME, mailUserName);
            content = content.replace(EmailMonitorConstants.GMAIL_ACCESS_TOKEN, mailAccessToken);
            content = content.replace(EmailMonitorConstants.GMAIL_CLIENT_ID, mailClientId);
            content = content.replace(EmailMonitorConstants.GMAIL_CLIENT_SECRET, mailClientSecret);
            content = content.replace(EmailMonitorConstants.GMAIL_REFRESH_TOKEN, mailRefreshToken);

            CryptographyManager cryptographyManager = new CryptographyManager();
            content = content.replace(EmailMonitorConstants.GMAIL_PASSWORD, cryptographyManager.encryptAndBase64Encode("ggg"));


            OMElement omElementTask = null;
            try {
                omElementTask = AXIOMUtil.stringToOM(content);
            } catch (XMLStreamException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when creating OMElement in Task", e);
            }

            try {
                stub.addTaskDescription(omElementTask);
            } catch (RemoteException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when adding Tasks ", e);
            } catch (TaskManagementException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when adding Tasks ", e);
            }


    }


    public void removeTask() throws EmailMonitorServiceException {
        boolean isTaskExist = false;
        try {
            isTaskExist = stub.isContains(EmailMonitorConstants.TASK_NAME, EmailMonitorConstants.TASK_GROUP);
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when searching for task 'getmail' ", e);
        } catch (TaskManagementException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when searching for task 'getmail' ", e);
        }

        if(isTaskExist){
            try {
                stub.deleteTaskDescription(EmailMonitorConstants.TASK_NAME,EmailMonitorConstants.TASK_GROUP);
            } catch (RemoteException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when deleting Tasks ", e);
            } catch (TaskManagementException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when deleting Tasks ", e);
            }

        }


    }
}
