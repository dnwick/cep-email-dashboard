package org.wso2.cep.email.monitor.internal.ds;


import org.wso2.carbon.event.formatter.core.EventFormatterService;
import org.wso2.carbon.event.output.adaptor.manager.core.OutputEventAdaptorManagerService;
import org.wso2.carbon.event.processor.core.EventProcessorService;
import org.wso2.carbon.event.stream.manager.core.EventStreamService;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.utils.ConfigurationContextService;
import org.wso2.cep.email.monitor.EmailMonitorServiceInterface;
import org.wso2.cep.email.monitor.internal.EmailMonitorService;

public class EmailMonitorValueHolder {

    private EmailMonitorServiceInterface emailMonitorServiceInterface;
    private static EmailMonitorValueHolder emailMonitorValueHolder;
    private ConfigurationContextService configurationContextService;
    private EventStreamService eventStreamService;
    private EventProcessorService eventProcessorService;
    private OutputEventAdaptorManagerService outputEventAdaptorManagerService;
    private EventFormatterService eventFormatterService;
    private RegistryService registryService;


    private EmailMonitorValueHolder() {
        emailMonitorServiceInterface = new EmailMonitorService();
    }


    public EmailMonitorService getEmailMonitorService() {
        return (EmailMonitorService) emailMonitorServiceInterface;
    }



    public static EmailMonitorValueHolder getInstance() {
        if (emailMonitorValueHolder == null) {
            emailMonitorValueHolder = new EmailMonitorValueHolder();
        }
        return emailMonitorValueHolder;
    }

    public void registerConfigurationContextService(ConfigurationContextService configurationContextService) {
        this.configurationContextService = configurationContextService;
    }
    public EventStreamService getEventStreamService() {
        return  eventStreamService;
    }

    public void setEventStreamService(EventStreamService eventStreamService){
       this.eventStreamService = eventStreamService;
    }
    public void unsetEventStreamService(){
        this.eventStreamService = null;
    }

    public EventProcessorService getEventProcessorService() {
        return  eventProcessorService;
    }

    public OutputEventAdaptorManagerService getOutputEventAdaptorManagerService() {
        return  outputEventAdaptorManagerService;
    }

    public void setEventFormatterService(EventFormatterService eventFormatterService) {
        this.eventFormatterService = eventFormatterService;
    }
    public void unsetEventFormatterService(EventFormatterService eventFormatterService) {
        this.eventFormatterService = null;
    }

    public void setOutputEventAdaptorManagerService(OutputEventAdaptorManagerService outputEventAdaptorManagerService) {
        this.outputEventAdaptorManagerService = outputEventAdaptorManagerService;
    }
    public void unsetsetOutputEventAdaptorManagerService(OutputEventAdaptorManagerService outputEventAdaptorManagerService) {
        this.outputEventAdaptorManagerService = null;
    }

    public EventFormatterService getEventFormatterService(){
        return  eventFormatterService;
    }

    public void setEventProcessorService(EventProcessorService eventProcessorService){
        this.eventProcessorService = eventProcessorService;
    }
    public void unsetEventProcessorService(){
        this.eventProcessorService = null;
    }

    public RegistryService getRegistryService() {
        return registryService;
    }

    public void setRegistryService(RegistryService registryService){
        this.registryService= registryService;
    }
    public void unsetRegistryService(){
        this.registryService = null;
    }
}
