package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.Stack;

public class Transformation {
    private static String getNextToken(String expression, int position) {
        if (position >= expression.length()) {
            return "";
        }
        char firstSymbol = expression.charAt(position);
        if ("+-*/()".indexOf(firstSymbol) != -1) {
            return String.valueOf(firstSymbol);
        } else if (Character.isDigit(firstSymbol)) {
            StringBuilder result = new StringBuilder();
            while (position < expression.length() && Character.isDigit(expression.charAt(position))) {
                result.append(expression.charAt(position));
                ++position;
            }
            return result.toString();
        } else if (Character.isLetter(firstSymbol)) {
            StringBuilder result = new StringBuilder();
            while (position < expression.length() && Character.isLetterOrDigit(expression.charAt(position))) {
                result.append(expression.charAt(position));
                ++position;
            }
            return result.toString();
        }
        return "";
    }

    public static ArrayList<String> getReversePolish(String expression) {
        ArrayList<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        int position = 0;
        while (position < expression.length()) {
            String token = getNextToken(expression, position);
            if (token.isEmpty()) {
                ++position;
                continue;
            }
            position += token.length();
            if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if ("*/".contains(token)) {
                while (!stack.isEmpty() && ("*/".contains(stack.peek()))) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else if ("+-".contains(token)) {
                while (!stack.isEmpty() && "+-*/".contains(stack.peek())) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else {
                output.add(token);
            }
        }
        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
        return output;
    }

    public static Expression getExpression(String expression) {
        ArrayList<String> rpn = getReversePolish(expression);
        Stack<Expression> result = new Stack<>();
        for (String s : rpn) {
            if (s.matches("\\d+")) {
                result.push(new Number(Integer.parseInt(s)));
            } else if (s.equals("+")) {
                Expression right = result.pop();
                Expression left = result.pop();
                result.push(new Add(left, right));
            } else if (s.equals("-")) {
                Expression right = result.pop();
                Expression left = result.pop();
                result.push(new Sub(left, right));
            } else if (s.equals("*")) {
                Expression right = result.pop();
                Expression left = result.pop();
                result.push(new Mul(left, right));
            } else if (s.equals("/")) {
                Expression right = result.pop();
                Expression left = result.pop();
                result.push(new Div(left, right));
            } else {
                result.push(new Variable(s));
            }
        }
        return result.pop();
    }
}
