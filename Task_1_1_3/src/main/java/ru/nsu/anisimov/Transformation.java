package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The Transformation class provides methods for converting infix expressions into
 * reverse Polish notation (RPN) and for generating corresponding Expression objects.
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
        } else if (Character.isDigit(firstSymbol) || firstSymbol == '.') {
            StringBuilder result = new StringBuilder();
            while (position < expression.length()
                   && (Character.isDigit(expression.charAt(position))
                       || expression.charAt(position) == '.')) {
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
        return expression;
    }

    /**
     * Converts an infix expression to a reverse Polish notation (RPN) format.
     * The method uses a stack to process operators and ensures that the precedence
     * of operators is handled correctly.
     *
     * @param expression the infix expression as a string
     * @return A list of tokens in RPN order.
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
                if (!stack.isEmpty() && stack.peek().equals("(")) {
                    stack.pop();
                }
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek())
                       && precedence(stack.peek()) >= precedence(token)) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else {
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            String op = stack.pop();
            output.add(op);
        }

        return output;
    }

    /**
     * Generates an Expression object from an infix string representation of a mathematical expression.
     * This method uses reverse Polish notation to evaluate and structure the expression.
     *
     * @param expression the infix expression as a string
     * @return An Expression object representing the parsed expression
     */
    public static Expression getExpression(String expression) {
        ArrayList<String> rpn = getReversePolish(expression);
        Stack<Expression> stack = new Stack<>();
        for (String token : rpn) {
            if (isNumber(token)) {
                stack.push(new Number(Double.parseDouble(token)));
            } else if (isOperator(token)) {
                Expression right = stack.pop();
                Expression left = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(new Add(left, right));
                        break;
                    case "-":
                        stack.push(new Sub(left, right));
                        break;
                    case "*":
                        stack.push(new Mul(left, right));
                        break;
                    case "/":
                        stack.push(new Div(left, right));
                        break;
                }
            } else {
                stack.push(new Variable(token));
            }
        }
        return stack.pop();
    }

    /**
     * Determines if a token is an operator.
     *
     * @param token the token to check
     * @return True if the token is an operator, false otherwise
     */
    private static boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    /**
     * Determines the precedence of an operator.
     *
     * @param operator the operator
     * @return The precedence level
     */
    private static int precedence(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else if (operator.equals("*") || operator.equals("/")) {
            return 2;
        }
        return 0;
    }

    /**
     * Determines if a token is a number.
     *
     * @param token the token to check
     * @return True if the token is a number, false otherwise
     */
    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}