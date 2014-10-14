package org.wso2.cep.email.monitor.query.api.actions;


import org.wso2.cep.email.monitor.query.api.attribute.EmailAddress;

public class SendMail extends Action {



    public SendMail(){

    }

    public SendMail(EmailAddress emailAddress){
        setEmailAddress(emailAddress);

    }



    @Override
    public String toString() {
        return "send mail (to " + getEmailAddress() + " subject " + getSubject() + " body" + getBody() + ")";
    }
}
