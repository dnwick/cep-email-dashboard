package org.wso2.cep.email.monitor.query.api;


import org.wso2.cep.email.monitor.query.api.actions.Action;
import org.wso2.cep.email.monitor.query.api.conditions.ConditionAttribute;

public class Query {

  private ConditionAttribute conditionAttribute;
  private Action action;


    public Query(){

    }

    public Query(ConditionAttribute conditionAttribute,Action action){
        this.conditionAttribute = conditionAttribute;
        this.action = action;
    }


    public ConditionAttribute getConditionAttribute() {
        return conditionAttribute;
    }

    public void setConditionAttribute(ConditionAttribute conditionAttribute) {
        this.conditionAttribute = conditionAttribute;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "if "+ conditionAttribute.toString()+  "then  "+action.toString();
    }


}
