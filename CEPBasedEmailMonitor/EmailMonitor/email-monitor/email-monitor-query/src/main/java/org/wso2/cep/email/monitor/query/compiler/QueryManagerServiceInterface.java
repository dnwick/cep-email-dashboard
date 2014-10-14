package org.wso2.cep.email.monitor.query.compiler;


import org.wso2.cep.email.monitor.query.compiler.exeception.EmailMonitorCompilerException;

public interface QueryManagerServiceInterface {



    public String[] getSiddhiQuery(String query) throws EmailMonitorCompilerException;












}
