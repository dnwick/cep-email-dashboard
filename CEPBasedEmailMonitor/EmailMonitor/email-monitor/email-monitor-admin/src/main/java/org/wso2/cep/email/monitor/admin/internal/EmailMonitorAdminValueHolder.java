package org.wso2.cep.email.monitor.admin.internal;


import org.wso2.cep.email.monitor.EmailMonitorServiceInterface;
import org.wso2.cep.email.monitor.query.compiler.QueryManagerServiceInterface;

public class EmailMonitorAdminValueHolder {

    private static EmailMonitorAdminValueHolder emailMonitorAdminValueHolder;
    private EmailMonitorServiceInterface emailMonitorServiceInterface;
    private QueryManagerServiceInterface queryManagerServiceInterface;

    private EmailMonitorAdminValueHolder() {

    }

    public static EmailMonitorAdminValueHolder getInstance() {
        if (emailMonitorAdminValueHolder == null) {
            emailMonitorAdminValueHolder = new EmailMonitorAdminValueHolder();
        }
        return emailMonitorAdminValueHolder;
    }

    public QueryManagerServiceInterface getQueryManagerServiceInterface() {
        return queryManagerServiceInterface;
    }

    public void setQueryManagerServiceInterface(QueryManagerServiceInterface queryManagerServiceInterface) {
        this.queryManagerServiceInterface = queryManagerServiceInterface;
    }
    public void unsetQueryManagerServiceInterface(QueryManagerServiceInterface queryManagerServiceInterface) {
        this.queryManagerServiceInterface = null;
    }

    public void registerEmailMonitorService(EmailMonitorServiceInterface emailMonitorServiceInterface) {
        this.emailMonitorServiceInterface = emailMonitorServiceInterface;
    }

    public void unregisterEmailMonitorService() {
        this.emailMonitorServiceInterface = null;
    }

    public EmailMonitorServiceInterface getEmailMonitorService() {
        return emailMonitorServiceInterface;
    }

}
