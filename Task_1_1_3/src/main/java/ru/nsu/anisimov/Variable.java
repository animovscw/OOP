package ru.nsu.anisimov;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class is used to represent variables in mathematical expressions.
 */
public class Variable extends Expression {
    public String variableName;

    public Variable(String name) {
        this.variableName = name;
    }

    /**
     * The method parses strings containing variable assignments
     * and stores these value in a dictionary.
     *
     * @param input the variable string
     * @return Dictionary of entries
     */
    private static Dictionary<String, Integer> parseAssignations(String input) {
        Dictionary<String, Integer> dictionary = new Hashtable<>();
        Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*(\\d+)(;?)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String key = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));
            dictionary.put(key, value);
        }

        return dictionary;
    }

    /**
     * The method calculates the value of a variable using a string of variable assignments.
     *
     * @param assignation the string
     * @return If there is a value for a variable in the dictionary, that value is returned
     */
    @Override
    public double evaluate(String assignation) {
        Dictionary<String, Integer> assignations = parseAssignations(assignation);

        return assignations.get(this.variableName);
    }

    /**
     * The method returns the derivative of a variable with respect to a given variable.
     *
     * @param variable the variable
     * @return 1 if the variable matches the variable name, 0 otherwise
     */
    @Override
    public Expression getDerivative(String variable) {
        if (variable.equals(this.variableName)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * The method returns a simplified version of the variable.
     *
     * @return A copy of the variable itself
     */
    @Override
    public Expression getSimplified() {
        return new Variable(this.variableName);
    }

    /**
     * The method returns a string representation of a variable.
     *
     * @return The variableName
     */
    @Override
    public String toString() {
        return this.variableName;
    }
}