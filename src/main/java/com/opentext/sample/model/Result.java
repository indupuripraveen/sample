package com.opentext.sample.model;

public class Result {

    public String a;
    public String b;
    public String operator;

    public Result(String a, String b, String operator, String result, String error) {
        this.a = a;
        this.b = b;
        this.operator = operator;
        this.result = result;
        this.error = error;
    }

    public String result;
    public String error;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
