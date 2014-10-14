package org.wso2.cep.email.monitor.query.api.attribute;


public class Label extends Attribute {

    public Label(){

    }

   public Label(String value){
       setValue(value);
   }






    @Override
    public String toString() {
        return getValue();
    }
}
