package org.wso2.cep.email.monitor.exception;



public class EmailMonitorServiceException extends  Exception{


   public EmailMonitorServiceException(){}

    public EmailMonitorServiceException(String message){
        super(message);
    }

    public EmailMonitorServiceException(String message,Throwable e){
        super(message,e);
    }

    public EmailMonitorServiceException(Throwable e){
        super(e);
    }






}
