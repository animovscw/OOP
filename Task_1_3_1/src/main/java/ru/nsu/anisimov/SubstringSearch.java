package ru.nsu.anisimov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.io.Reader;

public class SubstringSearch {
    public static ArrayList<Long> resourceSearch(String resourceName, String subName) {
        ArrayList<Long> indexes = new ArrayList<>();
        int subLength = subName.length();
        StringBuilder current = new StringBuilder();
        long globalIndex = 0;

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

    public static ArrayList<Long> searchInReader(Reader reader, String subName) {
        ArrayList<Long> indexes = new ArrayList<>();
        int subLength = subName.length();
        StringBuilder current = new StringBuilder();
        long globalIndex = 0;

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            char[] buffer = new char[subLength];
            int readChars = bufferedReader.read(buffer);

            if (readChars < subLength) {
                return indexes;
            }

            current.append(buffer, 0, readChars);

            if (current.toString().equals(subName)) {
                indexes.add(globalIndex);
            }

            int nextChar;
            while ((nextChar = bufferedReader.read()) != -1) {
                current.deleteCharAt(0);
                current.append((char) nextChar);

                if (current.toString().equals(subName)) {
                    indexes.add(globalIndex - subLength + 1);
                }
                ++globalIndex;
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to read the resource");
        } catch (NullPointerException e) {
            throw new RuntimeException("Resource not found");
        }
        return indexes;
    }
}