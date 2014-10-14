package org.wso2.cep.email.monitor.query.api.operators;


import org.wso2.cep.email.monitor.query.api.expressions.Expression;

public abstract class Operator {

    private Expression left;
    private Expression right;


    public Operator() {

    }

    public Operator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }
}
