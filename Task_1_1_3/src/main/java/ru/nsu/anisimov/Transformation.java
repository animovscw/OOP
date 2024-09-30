package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.Stack;

public class Transformation {
    private static String getNextToken(String exp, int position) {
        if (position >= exp.length()) {
            return "";
        }
        char firstSymbol = exp.charAt(position);
        if ("+-*/()".indexOf(firstSymbol) != -1) {
            return String.valueOf(firstSymbol);
        } else if (Character.isDigit(firstSymbol)) {
            StringBuilder result = new StringBuilder();
            while (position < exp.length() && Character.isDigit(exp.charAt(position))) {
                result.append(exp.charAt(position));
                ++position;
            }
            return result.toString();
        } else if (Character.isLetter(firstSymbol)) {
            StringBuilder result = new StringBuilder();
            while (position < exp.length() && Character.isLetterOrDigit(exp.charAt(position))) {
                result.append(exp.charAt(position));
                ++position;
            }
            return result.toString();
        }
        return "";
    }

    public static ArrayList<String> getReversePolish(String exp) {
        ArrayList<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        int position = 0;
        while (position < exp.length()) {
            String token = getNextToken(exp, position);
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

    public static Expression getExpression(String exp) {
        ArrayList<String> rpn = getReversePolish(exp);
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
