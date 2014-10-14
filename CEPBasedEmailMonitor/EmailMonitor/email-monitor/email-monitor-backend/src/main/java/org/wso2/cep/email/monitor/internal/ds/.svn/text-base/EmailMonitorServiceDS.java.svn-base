package org.wso2.cep.email.monitor.internal.ds;


import org.apache.log4j.Logger;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.event.formatter.core.EventFormatterService;
import org.wso2.carbon.event.output.adaptor.manager.core.OutputEventAdaptorManagerService;
import org.wso2.carbon.event.processor.core.EventProcessorService;
import org.wso2.carbon.event.stream.manager.core.EventStreamService;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.cep.email.monitor.EmailMonitorServiceInterface;

/**
 * @scr.component name="emailmonitor.component" immediate="true"
 * @scr.reference name="eventstream.service"
 * interface="org.wso2.carbon.event.stream.manager.core.EventStreamService" cardinality="1..1"
 * policy="dynamic" bind="setEventStreamService" unbind="unsetEventStreamService"
 * @scr.reference name="eventprocessor.service"
 * interface="org.wso2.carbon.event.processor.core.EventProcessorService" cardinality="1..1"
 * policy="dynamic" bind="setEventProcessorService" unbind="unsetEventProcessorService"
 * @scr.reference name="eventformatter.service"
 * interface="org.wso2.carbon.event.formatter.core.EventFormatterService" cardinality="1..1"
 * policy="dynamic" bind="setEventFormatterService" unbind="unsetEventFormatterService"
 * @scr.reference name="outputeventadaptormanager.service"
 * interface="org.wso2.carbon.event.output.adaptor.manager.core.OutputEventAdaptorManagerService" cardinality="1..1"
 * policy="dynamic" bind="setOutputEventAdaptorService" unbind="unsetOutputEventAdaptorService"
 * @scr.reference name="registry.service"
 * interface="org.wso2.carbon.registry.core.service.RegistryService" cardinality="1..1"
 * policy="dynamic" bind="setRegistryService" unbind="unsetRegistryService"
 */
public class EmailMonitorServiceDS {
    private static final Logger log = Logger.getLogger(EmailMonitorServiceDS.class);

    /**
     * initialize the cep service here.
     *
     * @param context
     */
    protected void activate(ComponentContext context) {

        try {
            EmailMonitorServiceInterface emailMonitorServiceInterface = EmailMonitorValueHolder.getInstance().getEmailMonitorService();
            context.getBundleContext().registerService(EmailMonitorServiceInterface.class.getName(),
                    emailMonitorServiceInterface, null);
            log.info("Successfully deployed the Email Monitor Service");
        } catch (Throwable e) {
            log.error("Can not create the Email Monitor Service ", e);
        }
    }

    protected void deactivate(ComponentContext context) {
        // context.getBundleContext().ungetService();
    }

    protected void setEventStreamService(EventStreamService eventStreamService){
        EmailMonitorValueHolder.getInstance().setEventStreamService(eventStreamService);
    }

    protected void unsetEventStreamService(EventStreamService eventStreamService){
       EmailMonitorValueHolder.getInstance().unsetEventStreamService();
    }
    protected void setEventFormatterService(EventFormatterService eventFormatterService){
        EmailMonitorValueHolder.getInstance().setEventFormatterService(eventFormatterService);
    }

    protected void unsetEventFormatterService(EventFormatterService eventFormatterService){
        EmailMonitorValueHolder.getInstance().unsetEventFormatterService(eventFormatterService);
    }
    protected void setEventProcessorService(EventProcessorService eventProcessorService){
        EmailMonitorValueHolder.getInstance().setEventProcessorService(eventProcessorService);
    }

    protected void unsetEventProcessorService(EventProcessorService eventProcessorService){
        EmailMonitorValueHolder.getInstance().unsetEventProcessorService();
    }
    protected void setOutputEventAdaptorService(OutputEventAdaptorManagerService outputEventAdaptorManagerService){
        EmailMonitorValueHolder.getInstance().setOutputEventAdaptorManagerService(outputEventAdaptorManagerService);
    }

    protected void unsetOutputEventAdaptorService(OutputEventAdaptorManagerService outputEventAdaptorManagerService){
        EmailMonitorValueHolder.getInstance().unsetsetOutputEventAdaptorManagerService(outputEventAdaptorManagerService);
    }
    protected void setRegistryService(RegistryService registryService) {
        EmailMonitorValueHolder.getInstance().setRegistryService(registryService);
    }

    protected void unsetRegistryService(RegistryService registryService) {
        EmailMonitorValueHolder.getInstance().unsetRegistryService();
    }



}
