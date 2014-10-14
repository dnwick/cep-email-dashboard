package org.wso2.cep.email.monitor.internal.config;


import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.commons.Attribute;
import org.wso2.carbon.databridge.commons.AttributeType;
import org.wso2.carbon.databridge.commons.StreamDefinition;
import org.wso2.carbon.databridge.commons.exception.MalformedStreamDefinitionException;
import org.wso2.carbon.event.stream.manager.core.exception.EventStreamConfigurationException;

import org.wso2.carbon.event.stream.manager.core.EventStreamService;

import org.wso2.cep.email.monitor.exception.EmailMonitorServiceException;

import org.wso2.cep.email.monitor.internal.ds.EmailMonitorValueHolder;

import java.util.ArrayList;
import java.util.List;


public class StreamDeployer {

    private static final Logger logger = Logger.getLogger(StreamDeployer.class);

    EventStreamService eventStreamService;
    private EmailMonitorValueHolder emailMonitorValueHolder;

    public StreamDeployer() throws EmailMonitorServiceException {

        emailMonitorValueHolder = EmailMonitorValueHolder.getInstance();
        eventStreamService = emailMonitorValueHolder.getEventStreamService();

    }


    public void createMailInputStream(int tenantID) throws EmailMonitorServiceException {
        StreamDefinition streamDefinition;
        try {
            streamDefinition = eventStreamService.getStreamDefinition("gmailInputStream", "1.0.0", tenantID);
        } catch (EventStreamConfigurationException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when getting stream definition", e);
        }
        if (streamDefinition == null) {
            try {
                streamDefinition = new StreamDefinition("gmailInputStream", "1.0.0");
            } catch (MalformedStreamDefinitionException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error defining new stream", e);
            }

            List<Attribute> metaData = new ArrayList<Attribute>();

            Attribute metaTenantID = new Attribute("tenant_id", AttributeType.INT);
            metaData.add(metaTenantID);

            Attribute metaHttpMethod = new Attribute("http_method", AttributeType.STRING);
            metaData.add(metaHttpMethod);

            Attribute metaCharacterSet = new Attribute("character_set_encoding", AttributeType.STRING);
            metaData.add(metaCharacterSet);

            Attribute metaRemoteAddress = new Attribute("remote_address", AttributeType.STRING);
            metaData.add(metaRemoteAddress);

            Attribute metaTransportIn = new Attribute("transport_in_url", AttributeType.STRING);
            metaData.add(metaTransportIn);

            Attribute metaMessageType = new Attribute("message_type", AttributeType.STRING);
            metaData.add(metaMessageType);

            Attribute metaRemoteHost = new Attribute("remote_host", AttributeType.STRING);
            metaData.add(metaRemoteHost);

            Attribute metaServicePrefix = new Attribute("service_prefix", AttributeType.STRING);
            metaData.add(metaServicePrefix);

            Attribute metaHost = new Attribute("host", AttributeType.STRING);
            metaData.add(metaHost);

            streamDefinition.setMetaData(metaData);


            List<Attribute> correlationData = new ArrayList<Attribute>();

            Attribute correlationActivityID = new Attribute("activity_id", AttributeType.STRING);
            correlationData.add(correlationActivityID);

            streamDefinition.setCorrelationData(correlationData);

            List<Attribute> payloadData = new ArrayList<Attribute>();

            Attribute payloadMessageDirection = new Attribute("message_direction", AttributeType.STRING);
            payloadData.add(payloadMessageDirection);

            Attribute payloadServiceName = new Attribute("service_name", AttributeType.STRING);
            payloadData.add(payloadServiceName);

            Attribute payloadOperationName = new Attribute("operation_name", AttributeType.STRING);
            payloadData.add(payloadOperationName);

            Attribute payloadMessageID = new Attribute("message_id", AttributeType.STRING);
            payloadData.add(payloadMessageID);

            Attribute payloadTimestamp = new Attribute("timestamp", AttributeType.LONG);
            payloadData.add(payloadTimestamp);

            Attribute payloadEmailMessageID = new Attribute("messageID", AttributeType.LONG);
            payloadData.add(payloadEmailMessageID);

            Attribute payloadSubject = new Attribute("subject", AttributeType.STRING);
            payloadData.add(payloadSubject);

            Attribute payloadFrom = new Attribute("sender", AttributeType.STRING);
            payloadData.add(payloadFrom);

            Attribute payloadTo = new Attribute("to", AttributeType.STRING);
            payloadData.add(payloadTo);

            Attribute payloadSentDate = new Attribute("sentDate", AttributeType.LONG);
            payloadData.add(payloadSentDate);

            Attribute payloadThreadID = new Attribute("threadID", AttributeType.LONG);
            payloadData.add(payloadThreadID);

            Attribute payloadStatus = new Attribute("status", AttributeType.STRING);
            payloadData.add(payloadStatus);

            Attribute payloadContent = new Attribute("content", AttributeType.STRING);
            payloadData.add(payloadContent);

            Attribute payloadLabels = new Attribute("labels", AttributeType.STRING);
            payloadData.add(payloadLabels);

            Attribute payloadSoapHeader = new Attribute("soap_header", AttributeType.STRING);
            payloadData.add(payloadSoapHeader);

            Attribute payloadSoapBody = new Attribute("soap_body", AttributeType.STRING);
            payloadData.add(payloadSoapBody);

            streamDefinition.setPayloadData(payloadData);
            streamDefinition.setDescription("email information stream");
            streamDefinition.setNickName("gmail");

            try {
                eventStreamService.addEventStreamDefinition(streamDefinition, tenantID);
            } catch (EventStreamConfigurationException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when adding new stream", e);
            }

        }
    }


    public void createMailOutputStream(int tenantID) throws EmailMonitorServiceException {
        StreamDefinition streamDefinition;
        try {
            streamDefinition = eventStreamService.getStreamDefinition("gmailOutputStream", "1.0.0", tenantID);
        } catch (EventStreamConfigurationException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when getting stream definition", e);
        }

        if (streamDefinition == null) {
            try {
                streamDefinition = new StreamDefinition("gmailOutputStream", "1.0.0");
            } catch (MalformedStreamDefinitionException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error defining new stream", e);
            }

            List<Attribute> payloadData = new ArrayList<Attribute>();

            Attribute payloadThreadID = new Attribute("threadID", AttributeType.LONG);
            payloadData.add(payloadThreadID);

            Attribute payloadLabel = new Attribute("label", AttributeType.STRING);
            payloadData.add(payloadLabel);

            streamDefinition.setPayloadData(payloadData);
            streamDefinition.setDescription("analyzed email stream");
            streamDefinition.setNickName("gmail_output");

            try {
                eventStreamService.addEventStreamDefinition(streamDefinition, tenantID);
            } catch (EventStreamConfigurationException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when adding new stream", e);
            }
        }
    }

    public void createThreadDetailsStream(int tenantID) throws EmailMonitorServiceException {
        StreamDefinition streamDefinition;
        try {
            streamDefinition = eventStreamService.getStreamDefinition("threadDetails", "1.0.0", tenantID);
        } catch (EventStreamConfigurationException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when getting stream definition", e);
        }

        if (streamDefinition == null) {
            try {
                streamDefinition = new StreamDefinition("threadDetails", "1.0.0");
            } catch (MalformedStreamDefinitionException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error defining new stream", e);
            }

            List<Attribute> payloadData = new ArrayList<Attribute>();

            Attribute payloadThreadID = new Attribute("threadID", AttributeType.LONG);
            payloadData.add(payloadThreadID);

            Attribute payloadLabels = new Attribute("labels", AttributeType.STRING);
            payloadData.add(payloadLabels);

            Attribute payloadEmailCount = new Attribute("emailCount", AttributeType.LONG);
            payloadData.add(payloadEmailCount);

            Attribute payloadTo = new Attribute("to", AttributeType.STRING);
            payloadData.add(payloadTo);

            Attribute payloadSenders = new Attribute("senders", AttributeType.STRING);
            payloadData.add(payloadSenders);

            Attribute payloadNewLabel = new Attribute("newLabel", AttributeType.STRING);
            payloadData.add(payloadNewLabel);


            streamDefinition.setPayloadData(payloadData);
            streamDefinition.setDescription("thread detail stream");
            streamDefinition.setNickName("thread detail");

            try {
                eventStreamService.addEventStreamDefinition(streamDefinition, tenantID);
            } catch (EventStreamConfigurationException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when adding new stream", e);
            }
        }

    }



    public void createLabelDetailsStream(int tenantID) throws EmailMonitorServiceException {
        StreamDefinition streamDefinition;
        try {
            streamDefinition = eventStreamService.getStreamDefinition("labelDetails", "1.0.0", tenantID);
        } catch (EventStreamConfigurationException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when getting stream definition", e);
        }

        if (streamDefinition == null) {
            try {
                streamDefinition = new StreamDefinition("labelDetails", "1.0.0");
            } catch (MalformedStreamDefinitionException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error defining new stream", e);
            }

            List<Attribute> payloadData = new ArrayList<Attribute>();

            Attribute payloadThreadCount = new Attribute("threadCount", AttributeType.LONG);
            payloadData.add(payloadThreadCount);

            Attribute payloadTo = new Attribute("to", AttributeType.STRING);
            payloadData.add(payloadTo);

            Attribute payloadSubject = new Attribute("subject", AttributeType.STRING);
            payloadData.add(payloadSubject);

            Attribute payloadContent = new Attribute("content", AttributeType.STRING);
            payloadData.add(payloadContent);

            Attribute payloadLabel = new Attribute("label", AttributeType.STRING);
            payloadData.add(payloadLabel);


            streamDefinition.setPayloadData(payloadData);
            streamDefinition.setDescription("label detail stream");
            streamDefinition.setNickName("label detail");

            try {
                eventStreamService.addEventStreamDefinition(streamDefinition, tenantID);
            } catch (EventStreamConfigurationException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when adding new stream", e);
            }
        }

    }


    public void createEmailSenderOutputStream(int tenantID) throws EmailMonitorServiceException {
        StreamDefinition streamDefinition;
        try {
            streamDefinition = eventStreamService.getStreamDefinition("emailSenderOutputStream", "1.0.0", tenantID);
        } catch (EventStreamConfigurationException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when getting stream definition", e);
        }

        if (streamDefinition == null) {
            try {
                streamDefinition = new StreamDefinition("emailSenderOutputStream", "1.0.0");
            } catch (MalformedStreamDefinitionException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error defining new stream", e);
            }

            List<Attribute> payloadData = new ArrayList<Attribute>();

            Attribute payloadThreadCount = new Attribute("threadCount", AttributeType.LONG);
            payloadData.add(payloadThreadCount);

            Attribute payloadTo = new Attribute("to", AttributeType.STRING);
            payloadData.add(payloadTo);

            Attribute payloadSubject = new Attribute("subject", AttributeType.STRING);
            payloadData.add(payloadSubject);

            Attribute payloadContent = new Attribute("content", AttributeType.STRING);
            payloadData.add(payloadContent);

            Attribute payloadLabel = new Attribute("label", AttributeType.STRING);
            payloadData.add(payloadLabel);

            streamDefinition.setPayloadData(payloadData);
            streamDefinition.setDescription("email sender");
            streamDefinition.setNickName("email_sender_output");

            try {
                eventStreamService.addEventStreamDefinition(streamDefinition, tenantID);
            } catch (EventStreamConfigurationException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when adding new stream", e);
            }
        }
    }

    public void createFilteredEmailDetailsStream(int tenantID) throws EmailMonitorServiceException {
        StreamDefinition streamDefinition;
        try {
            streamDefinition = eventStreamService.getStreamDefinition("filteredEmailDetails", "1.0.0", tenantID);
        } catch (EventStreamConfigurationException e) {
            logger.error(e.getMessage());
            throw new EmailMonitorServiceException("Error when getting stream definition", e);
        }

        if (streamDefinition == null) {
            try {
                streamDefinition = new StreamDefinition("filteredEmailDetails", "1.0.0");
            } catch (MalformedStreamDefinitionException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error defining new stream", e);
            }

            List<Attribute> payloadData = new ArrayList<Attribute>();

            Attribute payloadThreadID = new Attribute("threadID", AttributeType.LONG);
            payloadData.add(payloadThreadID);

            Attribute payloadLabels = new Attribute("labels", AttributeType.STRING);
            payloadData.add(payloadLabels);

            Attribute payloadTo = new Attribute("to", AttributeType.STRING);
            payloadData.add(payloadTo);

            Attribute payloadSenders = new Attribute("senders", AttributeType.STRING);
            payloadData.add(payloadSenders);

            Attribute payloadNewLabel = new Attribute("newLabel", AttributeType.STRING);
            payloadData.add(payloadNewLabel);


            streamDefinition.setPayloadData(payloadData);
            streamDefinition.setDescription("filtered email detail stream");
            streamDefinition.setNickName("filtered email detail");

            try {
                eventStreamService.addEventStreamDefinition(streamDefinition, tenantID);
            } catch (EventStreamConfigurationException e) {
                logger.error(e.getMessage());
                throw new EmailMonitorServiceException("Error when adding new stream", e);
            }

        }
    }

}

