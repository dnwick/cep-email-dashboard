package org.wso2.carbon.gmail.mediator;


import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPBody;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.synapse.util.xpath.SynapseXPath;
import org.jaxen.JaxenException;
import org.wso2.carbon.gmail.mediator.util.MediatorConstants;


import javax.xml.namespace.QName;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class MessageContentInjector extends AbstractMediator {
    public boolean mediate(MessageContext messageContext) {

        SOAPBody soapBody = messageContext.getEnvelope().getBody();
        OMElement mailOM = soapBody.getFirstElement();
        OMElement message = mailOM.getFirstElement().getFirstElement();
        removeIndentedContent(message);


        long time = getTimeStamp(message);
        OMElement omElement1 = (OMElement) message.getFirstChildWithName(new QName(MediatorConstants.GMAIL_NAME_SPACE_URL,"sentDate"));
        omElement1.setText(String.valueOf(time));
        omElement1.detach();
        message.addChild(omElement1);

        mailOM.addChild(message);
        return true;
    }


    private OMElement removeIndentedContent(OMElement omElement) {
        Iterator iterator = omElement.getChildrenWithLocalName(MediatorConstants.CONTENT);
        if (iterator.hasNext()) {
            OMElement omElement1 = (OMElement) iterator.next();
            String content = omElement1.getText();
            InputStream stream = new ByteArrayInputStream(content.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line;
            StringBuffer sb = new StringBuffer();
            try {
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(">")) {

                    }else if (line.startsWith("On") && line.endsWith("wrote:")){

                    }
                    else {
                        sb.append(line);
                        sb.append("\n");
                    }
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            omElement1.setText(sb.toString());
        }
        return omElement;
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
