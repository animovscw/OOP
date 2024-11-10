package ru.nsu.anisimov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class SubstringSearch {
    public static ArrayList<Integer> resourceSearch(String resourcename, String subname) throws IOException {
        ArrayList<Integer> indexes = new ArrayList<>();
        int subLength = subname.length();
        char[] buffer = new char[subLength];
        int globalIndex = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(SubstringSearch.class.getClassLoader().getResourceAsStream(resourcename)), StandardCharsets.UTF_8))) {

            int readChars = reader.read(buffer);
            if (readChars < subLength) {
                return indexes;
            }

            String current = new String(buffer);
            if (current.equals(subname)) {
                indexes.add(globalIndex);
            }
            ++globalIndex;

            int nextChar;
            while ((nextChar = reader.read()) != -1) {
                current = current.substring(1) + (char) nextChar;

                if (current.equals(subname)) {
                    indexes.add(globalIndex);
                }
                ++globalIndex;
            }
        }

        return indexes;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Integer> result = resourceSearch("text.txt", "бра");
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}