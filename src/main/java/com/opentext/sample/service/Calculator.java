package com.opentext.sample.service;


import com.opentext.sample.model.Result;

public class Calculator {

    private double leftOperand;
    private double rightOperand;
    private String operator;

    public Calculator(double leftOperand, double rightOperand, String operator) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
    }

    public double getLeftOperand() {
        return leftOperand;
    }

    public void setLeftOperand(double leftOperand) {
        this.leftOperand = leftOperand;
    }

    public double getRightOperand() {
        return rightOperand;
    }

    public void setRightOperand(double rightOperand) {
        this.rightOperand = rightOperand;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Result calculateResult() {

        Result result = new Result("" + leftOperand, "" + rightOperand, this.operator, "0.0", "");

        switch (this.operator) {
            case "a":
                result.setResult("" + (this.leftOperand + this.rightOperand));
                break;
            case "s":
                result.setResult("" + (this.leftOperand - this.rightOperand));
                break;
            case "m":
                result.setResult("" + (this.leftOperand * this.rightOperand));
                break;
            case "d":
                try {
                    double res = this.leftOperand / this.rightOperand;
                    result.setResult(String.valueOf(res));
                } catch (ArithmeticException e) {
                    result.setError("/ by zero");
                    System.out.println("/ by zero");
                }
                System.out.println(leftOperand + "/" + rightOperand);
                break;
            default:
                System.out.println("Other operator");
                result.setError("Invalid operator");
        }
        return result;
    }
}
