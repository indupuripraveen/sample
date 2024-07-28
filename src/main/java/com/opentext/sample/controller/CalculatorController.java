package com.opentext.sample.controller;

import com.opentext.sample.model.Result;
import com.opentext.sample.service.Calculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class CalculatorController {

    @GetMapping("/calculate")
    public Result calculate(@RequestParam(name = "a") String param1,
                            @RequestParam(name = "b") String param2,
                            @RequestParam(name = "operator") String operator) {
        // Example logic: sum of three parameters

        double leftNumber;
        double rightNumber;


        try {
            leftNumber = Double.parseDouble(param1);
        }
        catch (NumberFormatException ex) {
            leftNumber = 0;
        }

        try {
            rightNumber = Double.parseDouble(param2);
        }
        catch (NumberFormatException ex) {
            rightNumber = 0;
        }

        Calculator calculator = new Calculator(
                leftNumber,
                rightNumber,
                operator
        );

        System.out.println("operand is " +operator);

        Result result = calculator.calculateResult();

        return result;
    }

}

