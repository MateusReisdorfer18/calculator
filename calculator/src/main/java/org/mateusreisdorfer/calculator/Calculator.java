package org.mateusreisdorfer.calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Stack;

public class Calculator {
    public BigInteger startEquation(String equation) {
        Stack<Character> operatorStack = new Stack<>();
        StringBuilder output = new StringBuilder();
        equation = equation.replaceAll(" ", "");
        String[] charArray = equation.split("");

        for (String token : charArray) {
            if (token.matches("[0-9]")) {
                output.append(token);
            } else if (isOperator(token.charAt(0))) {
                while (!operatorStack.isEmpty() &&
                        precedence(operatorStack.peek()) >= precedence(token.charAt(0))) {
                    output.append(operatorStack.pop()).append(" ");
                }
                operatorStack.push(token.charAt(0));
            }
        }

        while(!operatorStack.isEmpty()) {
            output.append(operatorStack.pop());
        }

        return this.finishEquation(charArray);
    }

    private BigInteger finishEquation(String[] charArray) {
        Stack<BigDecimal> stack = new Stack<>();

        for (String token : charArray) {
            if (token.matches("[0-9]")) {
                stack.push(new BigDecimal(token));
            } else if (isOperator(token.charAt(0))) {
                BigDecimal secondNumber = stack.pop();
                BigDecimal firstNumber = stack.pop();
                stack.push(applyOperator(firstNumber, secondNumber, token.charAt(0)));
            }
        }

        return stack.pop().toBigInteger();
    }

    private BigDecimal applyOperator(BigDecimal firstNumber, BigDecimal secondNumber, char operator) {
        var result = firstNumber;

        switch (operator) {
            case '+': {
                result.add(secondNumber);
                break;
            }
            case '-': {
                result.subtract(secondNumber);
                break;
            }
            case '*': {
                result.multiply(secondNumber);
                break;
            }
            case '/': {
                if(secondNumber.compareTo(BigDecimal.ZERO) == 0)
                    throw new ArithmeticException("Cannot divide by zero");

                result.divide(secondNumber, 2, RoundingMode.UP);
                break;
            }
            default: {
                throw new IllegalArgumentException("Operador inválido: " + operator);
            }
        };

        return result;
    }

    private int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
