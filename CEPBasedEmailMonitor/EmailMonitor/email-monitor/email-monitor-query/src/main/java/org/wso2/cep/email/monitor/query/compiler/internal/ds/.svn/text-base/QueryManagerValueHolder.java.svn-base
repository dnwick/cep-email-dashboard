package org.wso2.cep.email.monitor.query.compiler.internal.ds;


import org.wso2.cep.email.monitor.query.compiler.QueryManagerServiceInterface;
import org.wso2.cep.email.monitor.query.compiler.internal.QueryManagerService;

public class QueryManagerValueHolder {
   private static QueryManagerValueHolder queryManagerValueHolder;

    private QueryManagerValueHolder(){

    }


    public QueryManagerServiceInterface getQueryManagerService(){
        return  new QueryManagerService();
    }




    public static QueryManagerValueHolder getInstance(){
        if(queryManagerValueHolder == null){
            queryManagerValueHolder = new QueryManagerValueHolder();
        }
        return  queryManagerValueHolder;
    }




}
