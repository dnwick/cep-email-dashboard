package org.wso2.cep.email.monitor.internal.config.esb.config;


import org.apache.axis2.AxisFault;

import org.wso2.carbon.proxyadmin.stub.ProxyServiceAdminProxyAdminException;
import org.wso2.carbon.proxyadmin.stub.ProxyServiceAdminStub;
import org.wso2.carbon.proxyadmin.stub.types.carbon.ProxyData;
import org.wso2.carbon.utils.CarbonUtils;
import org.apache.log4j.Logger;
import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;
import org.wso2.cep.email.monitor.internal.util.EmailMonitorConstants;

import java.rmi.RemoteException;


public class ProxyDeployer {

    private static final Logger logger = Logger.getLogger(ProxyDeployer.class);
    private ProxyServiceAdminStub stub;
    private XMLReader xmlReader;


    public ProxyDeployer(String ip, String port) throws EmailMonitorServiceException {
        xmlReader = new XMLReader();

        String endPoint = EmailMonitorConstants.PROTOCOL + ip + ":" + port + EmailMonitorConstants.SERVICES + EmailMonitorConstants.PROXY_ADMIN_SERVICE;

        try {
            stub = new ProxyServiceAdminStub(endPoint);
        } catch (AxisFault axisFault) {
            logger.error(axisFault.getMessage());
            throw new EmailMonitorServiceException("Error when creating ProxyDeployer", axisFault);
        }


    }

    public void addMailProxy(String userName, String password) throws EmailMonitorServiceException {

        CarbonUtils.setBasicAccessSecurityHeaders(userName, password, stub._getServiceClient());

        String proxyName = EmailMonitorConstants.MAIL_READER_PROXY_NAME;

        //Set proxy configuration data
        String[] transport = {"http", "https"};
        ProxyData data = new ProxyData();
        data.setName(proxyName);
        data.setStartOnLoad(true);
        data.setTransports(transport);
        String content = xmlReader.readXML(EmailMonitorConstants.MAIL_READER_PROXY_PATH);

        data.setInSeqXML(content);


        try {
            stub.deleteProxyService(EmailMonitorConstants.MAIL_READER_PROXY_NAME);
            stub.addProxy(data);
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding proxy", e);
        } catch (ProxyServiceAdminProxyAdminException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding proxy", e);
        }

    }

    public void addLabelAdderProxy(String userName, String password)throws EmailMonitorServiceException {

        CarbonUtils.setBasicAccessSecurityHeaders(userName, password, stub._getServiceClient());

        String proxyName = EmailMonitorConstants.LABEL_ADDER_PROXY_NAME;

        //Set proxy configuration data
        String[] transport = {"http", "https"};
        ProxyData data = new ProxyData();
        data.setName(proxyName);
        data.setStartOnLoad(true);
        data.setTransports(transport);
        String content = xmlReader.readXML(EmailMonitorConstants.LABEL_ADDER_PROXY_PATH);

        data.setInSeqXML(content);


        try {
            stub.deleteProxyService(EmailMonitorConstants.LABEL_ADDER_PROXY_NAME);
            stub.addProxy(data);
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding proxy", e);
        } catch (ProxyServiceAdminProxyAdminException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding proxy", e);
        }
    }


    public void addMailSenderProxy(String userName, String password)throws EmailMonitorServiceException {

        CarbonUtils.setBasicAccessSecurityHeaders(userName, password, stub._getServiceClient());

        String proxyName = EmailMonitorConstants.MAIL_SENDER_PROXY_NAME;

        //Set proxy configuration data
        String[] transport = {"http", "https"};
        ProxyData data = new ProxyData();
        data.setName(proxyName);
        data.setStartOnLoad(true);
        data.setTransports(transport);
        String content = xmlReader.readXML(EmailMonitorConstants.MAIL_SENDER_PROXY_PATH);

        data.setInSeqXML(content);


        try {
            stub.deleteProxyService(EmailMonitorConstants.MAIL_SENDER_PROXY_NAME);
            stub.addProxy(data);
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding proxy", e);
        } catch (ProxyServiceAdminProxyAdminException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when adding proxy", e);
        }
    }

    public void removeProxy() throws EmailMonitorServiceException {

        try {
                   stub.deleteProxyService(EmailMonitorConstants.MAIL_SENDER_PROXY_NAME);
                   stub.deleteProxyService(EmailMonitorConstants.LABEL_ADDER_PROXY_NAME);
                   stub.deleteProxyService(EmailMonitorConstants.MAIL_READER_PROXY_NAME);
        } catch (RemoteException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when removing proxy", e);
        } catch (ProxyServiceAdminProxyAdminException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when removing proxy", e);
        }


    }
}


