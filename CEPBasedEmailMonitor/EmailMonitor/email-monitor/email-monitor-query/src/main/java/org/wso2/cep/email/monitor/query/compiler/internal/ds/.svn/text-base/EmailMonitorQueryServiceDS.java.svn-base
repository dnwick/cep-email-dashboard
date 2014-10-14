package org.wso2.cep.email.monitor.query.compiler.internal.ds;

import org.apache.log4j.Logger;
import org.osgi.service.component.ComponentContext;
import org.wso2.cep.email.monitor.query.compiler.QueryManagerServiceInterface;

/**
 * @scr.component name="EmailMonitor.component" immediate="true"
 */
public class EmailMonitorQueryServiceDS {
    private static final Logger log = Logger.getLogger(EmailMonitorQueryServiceDS.class);

    protected void activate(ComponentContext context) {

        try {
            QueryManagerServiceInterface queryManagerServiceInterface = QueryManagerValueHolder.getInstance().getQueryManagerService();
            context.getBundleContext().registerService(QueryManagerServiceInterface.class.getName(),
                    queryManagerServiceInterface, null);
            log.info("Successfully deployed the Email Monitor Query Service");
        } catch (Throwable e) {
            log.error("Can not create the Email Monitor Query Service ", e);
        }
    }

    protected void deactivate(ComponentContext context) {
//        context.getBundleContext().ungetService();
    }


}
