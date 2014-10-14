package org.wso2.carbon.gmail.mediator;


import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.wso2.carbon.gmail.mediator.util.MediatorConstants;



import java.io.*;
import java.util.Iterator;

public class MessageContentInjector extends AbstractMediator {
    public boolean mediate(MessageContext messageContext) {

        SOAPBody soapBody = messageContext.getEnvelope().getBody();
        OMElement mailOM = soapBody.getFirstElement();
        OMElement message = mailOM.getFirstElement().getFirstElement();
        removeIndentedContent(message);
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


}
