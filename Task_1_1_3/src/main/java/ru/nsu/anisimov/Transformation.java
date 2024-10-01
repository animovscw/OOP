package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The class provides methods for converting infix expressions into reverse Polish notation (RPN)
 * and for generating corresponding objects.
 * It supports basic mathematical operations such as addition, subtraction, multiplication,
 * and division, as well as handling variables and numeric values.
 */
public class Transformation {
    /**
     * Retrieves the next token (number, operator, or variable) from the infix expression starting
     * from the specified position.
     *
     * @param expression the infix expression as a string
     * @param position   the current position to start parsing the token from
     * @return The next token as a string
     */
    private static String getNextToken(String expression, int position) {
        if (position >= expression.length()) {
            return "";
        }
        char firstSymbol = expression.charAt(position);
        if ("+-*/()".indexOf(firstSymbol) != -1) {
            return String.valueOf(firstSymbol);
        } else if (Character.isDigit(firstSymbol)) {
            StringBuilder result = new StringBuilder();
            while (position < expression.length()
                    && Character.isDigit(expression.charAt(position))) {
                result.append(expression.charAt(position));
                ++position;
            }
            return result.toString();
        } else if (Character.isLetter(firstSymbol)) {
            StringBuilder result = new StringBuilder();
            while (position < expression.length()
                    && Character.isLetterOrDigit(expression.charAt(position))) {
                result.append(expression.charAt(position));
                ++position;
            }
            return result.toString();
        }
        return "";
    }

    /**
     * Converts an infix expression to a reverse Polish notation (RPN) format.
     * The method uses a stack to process operators and ensures that the precedence
     * of operators is handled correctly.
     *
     * @param expression the infix expression as a string
     * @return A list
     */
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

    /**
     * Generates an object from an infix string representation of a mathematical expression.
     * This method uses reverse Polish notation to evaluate and structure the expression.
     *
     * @param expression the infix expression as a string
     * @return An object representing the parsed expression
     */
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
