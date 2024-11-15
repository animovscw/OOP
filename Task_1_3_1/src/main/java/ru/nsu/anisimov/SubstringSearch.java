package ru.nsu.anisimov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class SubstringSearch {
    public static ArrayList<Integer> resourceSearch(String resourceName, String subName) {
        ArrayList<Integer> indexes = new ArrayList<>();
        int subLength = subName.length();
        StringBuilder current = new StringBuilder();
        int globalIndex = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(SubstringSearch.class.getClassLoader().getResourceAsStream(resourceName)), StandardCharsets.UTF_8))) {

            char[] buffer = new char[subLength];
            int readChars = reader.read(buffer);

            if (readChars < subLength) {
                return indexes;
            }

            current.append(buffer, 0, readChars);
            if (current.toString().equals(subName)) {
                indexes.add(globalIndex);
            }
            ++globalIndex;

            int nextChar;
            while ((nextChar = reader.read()) != -1) {
                current.deleteCharAt(0);
                current.append((char) nextChar);

                if (current.toString().equals(subName)) {
                    indexes.add(globalIndex);
                }
                ++globalIndex;
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to read the resource: " + resourceName, e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Resource not found: " + resourceName, e);
        }

        return indexes;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Integer> result = resourceSearch("text.txt", "бра");
            System.out.println(result);
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
//            e.printStackTrace();
        }
    }
}