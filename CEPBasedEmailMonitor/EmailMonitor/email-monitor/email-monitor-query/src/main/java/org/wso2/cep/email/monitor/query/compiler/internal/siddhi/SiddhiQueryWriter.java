package org.wso2.cep.email.monitor.query.compiler.internal.siddhi;


import org.wso2.cep.email.monitor.query.api.Query;
import org.wso2.cep.email.monitor.query.api.utils.Constants;
import org.wso2.cep.email.monitor.query.compiler.internal.siddhi.utils.ConstantsUtils;


public class SiddhiQueryWriter {


    private static SiddhiQueryWriter siddhiQueryWriter;


    private SiddhiQueryWriter() {
    }

    public String[] writeQuery(Query query) {


        SiddhiTemplate siddhiTemplate = TemplatePopulator.convert(query);
        if (siddhiTemplate.isFreqEnabled()) {
            if (siddhiTemplate.getLabelMails() == null) {
                return writeQueryWithFrequencyWithoutLabel(siddhiTemplate);
            } else if (siddhiTemplate.isThreadFre()) {
                return writeQueryWithFrequencyWithLabelFrequency(siddhiTemplate);
            }else if(siddhiTemplate.isLabelCount()) {
                return writeQueryWithFrequencyWithLabel(siddhiTemplate);
            }else{
                return writeQueryWithFrequencyWithLabel(siddhiTemplate);
            }
        }


         else {
            if (siddhiTemplate.getLabelMails() != null) {
                return writeQueryWithoutFrequencyWithLabel(siddhiTemplate);
            } else if (siddhiTemplate.getLabelMails() == null) {
                return writeQueryWithoutFrequencyWithoutLabel(siddhiTemplate);
            }
        }

        return new String[2];
    }


    public String[] writeQueryWithoutFrequencyWithLabel(SiddhiTemplate siddhiTemplate) {
        String[] result = new String[2];
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("from " + ConstantsUtils.INPUTSTREAM);
        if (siddhiTemplate.getLabelMails() != null) {
            stringBuffer.append(" select  threadID,labels, email:getAll(to) as to ,email:getAll(sender) as senders,");
            if (!siddhiTemplate.isSendMailEnabled()) {
                stringBuffer.append(" " + '"' + siddhiTemplate.getLabelName() + '"' + " ");
                stringBuffer.append("as newLabel group by threadID insert into ");
                stringBuffer.append(ConstantsUtils.FILTERED_EMAIL_DETAILS);
                stringBuffer.append(";");
            }
        }
        result[0] = stringBuffer.toString();
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append("from " + ConstantsUtils.FILTERED_EMAIL_DETAILS);
        stringBuffer1.append("[");
        if (siddhiTemplate.getToMails() != null) {
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getToMails());
            stringBuffer1.append(")");

        }
        if (siddhiTemplate.getLabelMails() != null) {
            if(siddhiTemplate.getToMails()!= null) {
                stringBuffer1.append(" " + siddhiTemplate.getTolabel() + " ");
            }
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getLabelMails());
            stringBuffer1.append(")");

        }
        if (siddhiTemplate.getFromMails() != null) {
            stringBuffer1.append(siddhiTemplate.getLabelFrom());
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getFromMails());
            stringBuffer1.append(")");
        }
        stringBuffer1.append("]");
        if (!siddhiTemplate.isSendMailEnabled()) {
            stringBuffer1.append(" select threadID, newLabel as label insert into " + ConstantsUtils.OUTPUTSTREAM);
            stringBuffer1.append(";");
        }
        result[1] = stringBuffer1.toString();

        return result;
    }

    public String[] writeQueryWithoutFrequencyWithoutLabel(SiddhiTemplate siddhiTemplate) {
        String[] result = new String[2];
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("from " + ConstantsUtils.INPUTSTREAM);

            stringBuffer.append(" select  threadID,labels, email:getAll(to) as to ,email:getAll(sender) as senders,");
            if (!siddhiTemplate.isSendMailEnabled()) {
                stringBuffer.append(" " + '"' + siddhiTemplate.getLabelName() + '"' + " ");
                stringBuffer.append("as newLabel group by threadID insert into ");
                stringBuffer.append(ConstantsUtils.FILTERED_EMAIL_DETAILS);
                stringBuffer.append(";");
            }

        result[0] = stringBuffer.toString();
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append("from " + ConstantsUtils.FILTERED_EMAIL_DETAILS);
        stringBuffer1.append("[");
        if (siddhiTemplate.getToMails() != null) {
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getToMails());
            stringBuffer1.append(")");

        }

        if (siddhiTemplate.getFromMails() != null) {
            if(siddhiTemplate.getToMails()!= null) {
                stringBuffer1.append(siddhiTemplate.getLabelFrom());
            }
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getFromMails());
            stringBuffer1.append(")");
        }
        stringBuffer1.append("]");
        if (!siddhiTemplate.isSendMailEnabled()) {
            stringBuffer1.append(" select threadID, newLabel as label insert into " + ConstantsUtils.OUTPUTSTREAM);
            stringBuffer1.append(";");
        }
        result[1] = stringBuffer1.toString();

        return result;
    }

    public String[] writeQueryWithFrequencyWithoutLabel(SiddhiTemplate siddhiTemplate) {
        StringBuffer stringBuffer1 = new StringBuffer();
        String[] str = new String[2];

        stringBuffer1.append("from " + ConstantsUtils.INPUTSTREAM);

        if (!siddhiTemplate.isSendMailEnabled()) {
            if(siddhiTemplate.isLabelCount()) {
                if(siddhiTemplate.getCmpAction().equals("<=") || siddhiTemplate.getCmpAction().equals("<")){
                    stringBuffer1.append("#window.custom:externalTimeBatch(sentDate,30 days");
                }else {
                    stringBuffer1.append("#window.externalTime(sentDate,30 days");
                }
            }else {
                if(siddhiTemplate.getCmpAction().equals("<=") || siddhiTemplate.getCmpAction().equals("<")){
                    stringBuffer1.append("#window.custom:externalTimeBatch(sentDate,");
                }else {
                    stringBuffer1.append("#window.externalTime(sentDate,");
                }
                stringBuffer1.append(siddhiTemplate.getTimeExpr());

            }
            stringBuffer1.append(")");
            stringBuffer1.append(" select  threadID, labels,count(messageID) as emailCount, email:getAll(to) as to ,email:getAll(sender) as senders,");
            stringBuffer1.append(" " + '"' + siddhiTemplate.getLabelName() + '"' + " ");
            stringBuffer1.append("as newLabel group by threadID  having emailCount ");
            if(siddhiTemplate.getCmpAction().equals("=")){
                stringBuffer1.append("==");
                stringBuffer1.append(" " + siddhiTemplate.getCountValue());
            }else if(siddhiTemplate.getCmpAction().equals(">")) {
                stringBuffer1.append("==");
                stringBuffer1.append(" " + (siddhiTemplate.getCountValue()+1));
            }else if(siddhiTemplate.getCmpAction().equals(">=")) {
                stringBuffer1.append("==");
                stringBuffer1.append(" " + siddhiTemplate.getCountValue());
            }else {
                stringBuffer1.append(siddhiTemplate.getCmpAction());
                stringBuffer1.append(" " + siddhiTemplate.getCountValue());
            }

            stringBuffer1.append(" insert into " + ConstantsUtils.THREADSTREAM);
            stringBuffer1.append(";");
        }
        str[0] = stringBuffer1.toString();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("from " + ConstantsUtils.THREADSTREAM);
        if((siddhiTemplate.getToMails()==null) && (siddhiTemplate.getLabelMails()==null) &&(siddhiTemplate.getFromMails()==null)) {

        }else{
            stringBuffer.append("[");
        }
        if (siddhiTemplate.getToMails() != null) {
            stringBuffer.append("(");
            stringBuffer.append(siddhiTemplate.getToMails());
            stringBuffer.append(")");

        }
        if (siddhiTemplate.getFromMails() != null) {
            if(siddhiTemplate.getToMails()!=null) {
                stringBuffer.append(siddhiTemplate.getLabelFrom());
            }
            stringBuffer.append("(");
            stringBuffer.append(siddhiTemplate.getFromMails());
            stringBuffer.append(")");
        }
        if((siddhiTemplate.getToMails()==null) && (siddhiTemplate.getLabelMails()==null) &&(siddhiTemplate.getFromMails()==null)) {

        }else{
            stringBuffer.append("]");
        }
        if (!siddhiTemplate.isSendMailEnabled()) {
            stringBuffer.append(" select threadID, newLabel as label insert into " + ConstantsUtils.OUTPUTSTREAM);
            stringBuffer.append(";");
        }

        str[1] = stringBuffer.toString();


        return str;
    }

    public String[] writeQueryWithFrequencyWithLabel(SiddhiTemplate siddhiTemplate) {
        StringBuffer stringBuffer1 = new StringBuffer();
        String[] str = new String[2];

        stringBuffer1.append("from " + ConstantsUtils.INPUTSTREAM);


        if (!siddhiTemplate.isSendMailEnabled()) {

            if(siddhiTemplate.isLabelCount()) {
                if(siddhiTemplate.getCmpAction().equals("<=") || siddhiTemplate.getCmpAction().equals("<")){
                    stringBuffer1.append("#window.custom:externalTimeBatch(sentDate,30 days");
                }else {
                    stringBuffer1.append("#window.externalTime(sentDate,30 days");
                }
            }else {
                if(siddhiTemplate.getCmpAction().equals("<=") || siddhiTemplate.getCmpAction().equals("<")){
                    stringBuffer1.append("#window.custom:externalTimeBatch(sentDate,");
                }else {
                    stringBuffer1.append("#window.externalTime(sentDate,");
                }
                stringBuffer1.append(siddhiTemplate.getTimeExpr());

            }
            stringBuffer1.append(")");
            stringBuffer1.append(" select  threadID,labels, count(messageID) as emailCount, email:getAll(to) as to ,email:getAll(sender) as senders,");
            stringBuffer1.append(" " + '"' + siddhiTemplate.getLabelName() + '"' + " ");
            stringBuffer1.append("as newLabel group by threadID  having emailCount ");
            if(siddhiTemplate.getCmpAction().equals("=")){
                stringBuffer1.append("==");
                stringBuffer1.append(" " + siddhiTemplate.getCountValue());
            }else if(siddhiTemplate.getCmpAction().equals(">")) {
                stringBuffer1.append("==");
                stringBuffer1.append(" " + (siddhiTemplate.getCountValue()+1));
            }else if(siddhiTemplate.getCmpAction().equals(">=")) {
                stringBuffer1.append("==");
                stringBuffer1.append(" " + siddhiTemplate.getCountValue());
            }else {
                stringBuffer1.append(siddhiTemplate.getCmpAction());
                stringBuffer1.append(" " + siddhiTemplate.getCountValue());
            }
            stringBuffer1.append(" insert into " + ConstantsUtils.THREADSTREAM);
            stringBuffer1.append(";");
        }
        str[0] = stringBuffer1.toString();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("from " + ConstantsUtils.THREADSTREAM);
        stringBuffer.append("[");





        if (siddhiTemplate.getToMails() != null) {
            stringBuffer.append("(");
            stringBuffer.append(siddhiTemplate.getToMails());
            stringBuffer.append(")");

        }
        if (siddhiTemplate.getLabelMails() != null) {
            if(siddhiTemplate.getToMails()!= null) {
                stringBuffer.append(" " + siddhiTemplate.getTolabel() + " ");
            }
            stringBuffer.append("(");
            stringBuffer.append(siddhiTemplate.getLabelMails());
            stringBuffer.append(")");

        }
        if (siddhiTemplate.getFromMails() != null) {

            stringBuffer.append(siddhiTemplate.getLabelFrom());
            stringBuffer.append("(");
            stringBuffer.append(siddhiTemplate.getFromMails());
            stringBuffer.append(")");
        }
        stringBuffer.append("]");
        if (!siddhiTemplate.isSendMailEnabled()) {
            stringBuffer.append(" select threadID, newLabel as label insert into " + ConstantsUtils.OUTPUTSTREAM);
            stringBuffer.append(";");
        }
        str[1] = stringBuffer.toString();
        return str;
    }


    public String[] writeQueryWithFrequencyWithLabelFrequency(SiddhiTemplate siddhiTemplate) {
        StringBuffer stringBuffer1 = new StringBuffer();
        String[] str = new String[2];

        stringBuffer1.append("from " + ConstantsUtils.INPUTSTREAM);
        stringBuffer1.append("[");
        if (siddhiTemplate.getLabelMails() != null) {
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getLabelMails());
            stringBuffer1.append(")");
        }
        stringBuffer1.append("]");

        if(siddhiTemplate.isLabelCount()) {
            if(siddhiTemplate.getCmpAction().equals("<=") || siddhiTemplate.getCmpAction().equals("<")){
                stringBuffer1.append("#window.custom:externalTimeBatch(sentDate,30 days");
            }else {
                stringBuffer1.append("#window.externalTime(sentDate,30 days");
            }
        } else {

            if(siddhiTemplate.getCmpAction().equals("<=") || siddhiTemplate.getCmpAction().equals("<")){
                stringBuffer1.append("#window.custom:externalTimeBatch(sentDate,");
            }else {
                stringBuffer1.append("#window.externalTime(sentDate,");
            }
            stringBuffer1.append(siddhiTemplate.getTimeExpr());


        }
        stringBuffer1.append(")");
        stringBuffer1.append(" select  ");
        stringBuffer1.append(" email:getUniqueCount(threadID) as threadCount,");
        stringBuffer1.append(" " + '"'+siddhiTemplate.getTo()+'"' + " ");
        stringBuffer1.append(" as to,");
        stringBuffer1.append(" " + '"'+siddhiTemplate.getSubject()+'"' + " ");
        stringBuffer1.append(" as subject,");
        stringBuffer1.append(" " + '"'+siddhiTemplate.getBody()+'"' + " ");
        stringBuffer1.append(" as content,");
                String re = siddhiTemplate.getLabelMails();
        re = re.substring(re.indexOf('"') + 1, re.length() - 1);
        stringBuffer1.append(" " + '"' + re + '"' + " ");
        stringBuffer1.append(" as label insert into ");
        stringBuffer1.append(ConstantsUtils.LABELSTREAM);
        stringBuffer1.append(";");

        str[0] = stringBuffer1.toString();
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("from "+ ConstantsUtils.LABELSTREAM);
        stringBuffer.append(" [");
        stringBuffer.append("(");
        stringBuffer.append("threadCount ");
        if(siddhiTemplate.getCmpAction().equals("=")){
            stringBuffer.append("==");
            stringBuffer.append(" " + siddhiTemplate.getCountValue());
        }else if(siddhiTemplate.getCmpAction().equals(">")) {
            stringBuffer.append("==");
            stringBuffer.append(" " + (siddhiTemplate.getCountValue()+1));
        }else if(siddhiTemplate.getCmpAction().equals(">=")) {
            stringBuffer.append("==");
            stringBuffer.append(" " + siddhiTemplate.getCountValue());
        }else {
            stringBuffer.append(siddhiTemplate.getCmpAction());
            stringBuffer.append(" " + siddhiTemplate.getCountValue());
        }
        stringBuffer.append(" )");
        stringBuffer.append("]");
        stringBuffer.append("select  * insert into ");
        stringBuffer.append(ConstantsUtils.EMAIL_SENDER_OUTPUTSTREAM);
        stringBuffer.append(";");
        str[1] = stringBuffer.toString();

        return str;


    }


    public static SiddhiQueryWriter getInstance() {
        if (siddhiQueryWriter == null) {
            siddhiQueryWriter = new SiddhiQueryWriter();
        }
        return siddhiQueryWriter;
    }


}
