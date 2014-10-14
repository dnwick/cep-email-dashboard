package org.wso2.carbon.gmail.mediator;

import org.apache.axiom.om.*;
import org.apache.axiom.soap.SOAPBody;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.synapse.util.xpath.SynapseXPath;
import org.jaxen.JaxenException;
import org.wso2.carbon.gmail.mediator.util.MailSessionInfoStore;
import org.wso2.carbon.gmail.mediator.util.MediatorConstants;


import javax.xml.namespace.QName;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


public class MessageFilter extends AbstractMediator {


    public boolean mediate(MessageContext messageContext){
        int messageCount = MediatorConstants.DEFAULT_VALUE_ZERO;
        MailSessionInfoStore.incrementBundleCount();
        SOAPBody soapBody = messageContext.getEnvelope().getBody();
        OMElement mailOM = soapBody.getFirstElement();
        OMElement mailfirst = mailOM.getFirstElement();
        OMFactory omfactory = OMAbstractFactory.getOMFactory();
        OMElement selectedMails = omfactory.createOMElement(MediatorConstants.MESSAGES, MediatorConstants.GMAIL_NAME_SPACE_URL, MediatorConstants.NAME_SPACE_PREFIX);
        OMElement formattedMails = omfactory.createOMElement(MediatorConstants.MESSAGES, MediatorConstants.GMAIL_NAME_SPACE_URL, MediatorConstants.NAME_SPACE_PREFIX);
        Iterator iterator = mailfirst.getChildElements();
        while (iterator.hasNext()){
            OMElement omelement = (OMElement) iterator.next();
            long timeS = getTimeStamp(omelement);
            messageCount++;
            if (MailSessionInfoStore.getBundleCount() == MediatorConstants.MESSAGE_COUNT_STARTING_VALUE && messageCount == MediatorConstants.MESSAGE_COUNT_STARTING_VALUE) {
                MailSessionInfoStore.setPreviouseBundleFirstMailTS(timeS);
                selectedMails.addChild(omelement);

            } else if (MailSessionInfoStore.getBundleCount() > MediatorConstants.MESSAGE_COUNT_STARTING_VALUE) {

                    if (timeS <= MailSessionInfoStore.getPreviouseBundleFirstMailTS()) {
                        MailSessionInfoStore.setPreviouseMessageTS(timeS);
                        MailSessionInfoStore.setReadMailRecived(true);
                    } else {
                        selectedMails.addChild(omelement);
                    }
                    if ( MailSessionInfoStore.isReadMailRecived()){
                        MailSessionInfoStore.setPreviouseBundleFirstMailTS(MailSessionInfoStore.getNextBundleFirstMailTs());
                        MailSessionInfoStore.setReadMailRecived(false);
                        break;
                    }
                if (messageCount == MediatorConstants.MESSAGE_COUNT_STARTING_VALUE){
                    MailSessionInfoStore.setNextBundleFirstMailTs(timeS);
                }
            } else {
                MailSessionInfoStore.setPreviouseMessageTS(timeS);
                selectedMails.addChild(omelement);
            }
        }
        mailfirst.detach();

        Iterator iterator1 = selectedMails.getChildElements();
        while (iterator1.hasNext()) {
            OMElement omElement = (OMElement) iterator1.next();
            long time = getTimeStamp(omElement);
            OMElement omElement1 = (OMElement) omElement.getFirstChildWithName(new QName(MediatorConstants.GMAIL_NAME_SPACE_URL,"sentDate"));
            omElement1.setText(String.valueOf(time));
            omElement1.detach();
            omElement.addChild(omElement1);
            formattedMails.addChild(omElement);
        }
        mailOM.addChild(formattedMails);
        return true;
    }

    private long getTimeStamp(OMElement mailOM) {
        mailOM.build();
        mailOM.detach();
        long timstamp = MediatorConstants.DEFAULT_VALUE_ZERO;
        try {
            SynapseXPath synapseXPath = new SynapseXPath(MediatorConstants.XPATH_SEND_DATE);
            OMFactory omFactory = OMAbstractFactory.getOMFactory();
            OMNamespace omNamespace = omFactory.createOMNamespace(MediatorConstants.GMAIL_NAME_SPACE_URL, MediatorConstants.NAME_SPACE_PREFIX);
            synapseXPath.addNamespace(omNamespace);
            String result = (String) synapseXPath.stringValueOf(mailOM);
            SimpleDateFormat sdf = new SimpleDateFormat(MediatorConstants.DATE_FORMAT);
            try {
                Date date = sdf.parse(result);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                timstamp = c.getTimeInMillis();
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        } catch (JaxenException e) {
            log.error(e.toString());
        }
        return timstamp;
    }


}
