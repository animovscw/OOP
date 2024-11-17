package ru.nsu.anisimov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class for searching substring.
 */
public class SubstringSearch {
    /**
     * The method reads the content by character, sliding through the text's length.
     * All starting indices of matches saves in a list.
     *
     * @param resourceName the name of the resource
     * @param subName      the substring
     * @return a list of starting indices
     */
    public static ArrayList<Long> resourceSearch(String resourceName, String subName)
            throws ResourceReadException {
        ArrayList<Long> indexes = new ArrayList<>();
        int subLength = subName.length();
        StringBuilder current = new StringBuilder();
        long globalIndex = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(
                        SubstringSearch.class.getClassLoader().getResourceAsStream(resourceName)),
                StandardCharsets.UTF_8))
        ) {

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
            throw new ResourceReadException("Failed to read the resource: " + resourceName, e);
        } catch (NullPointerException e) {
            throw new ResourceReadException("Resource not found: " + resourceName, e);
        }

        return indexes;
    }

    /**
     * The method is the same as previous, but with only difference that it used to handle with
     * large file that generates in the process.
     *
     * @param reader  providing the text content
     * @param subName the substring
     * @return a list of starting indices
     * @throws IOException if an I/O error occurs while reading from the reader
     */
    public static ArrayList<Long> searchInReader(Reader reader, String subName)
            throws IOException {
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
                ++globalIndex;

                current.deleteCharAt(0);
                current.append((char) nextChar);

                if (current.toString().equals(subName)) {
                    indexes.add(globalIndex - subLength + 1);
                }
            }
        }
        return indexes;
    }
}