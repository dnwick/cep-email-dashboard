package org.wso2.cep.email.monitor.query.compiler.internal;


import org.wso2.cep.email.monitor.query.api.Query;
import org.wso2.cep.email.monitor.query.compiler.QueryManagerServiceInterface;
import org.wso2.cep.email.monitor.query.compiler.exeception.EmailMonitorCompilerException;
import org.wso2.cep.email.monitor.query.compiler.internal.siddhi.SiddhiQueryWriter;

public class QueryManagerService implements QueryManagerServiceInterface {





    public String[] getSiddhiQuery(String query) throws EmailMonitorCompilerException {

        Query queryTwo = null;
        try {
            queryTwo = EmailMonitorCompiler.parse(query);
            return SiddhiQueryWriter.getInstance().writeQuery(queryTwo);
        } catch (EmailMonitorCompilerException e) {
            throw new EmailMonitorCompilerException(e.getMessage(), e);
        }
    }
}
