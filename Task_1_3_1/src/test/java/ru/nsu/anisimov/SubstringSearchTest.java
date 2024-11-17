package ru.nsu.anisimov;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubstringSearchTest {
    @Test
    void testMultipleOccurrences() throws RuntimeException {
        ArrayList<Long> result = SubstringSearch.resourceSearch("text.txt", "бра");
        Assertions.assertEquals(result, new ArrayList<>(List.of(1L, 8L)));
    }

    @Test
    void testNoOccurrences() throws RuntimeException {
        ArrayList<Long> result = SubstringSearch.resourceSearch("text.txt", "bra");
        Assertions.assertEquals(new ArrayList<>(), result);
    }

    @Test
    void testEmptyFile() throws RuntimeException {
        ArrayList<Long> result = SubstringSearch.resourceSearch("textEmpty.txt", "abra");
        Assertions.assertEquals(new ArrayList<>(), result);
    }

    @Test
    void testSubstringLongerThanFile() throws RuntimeException {
        ArrayList<Long> result = SubstringSearch.resourceSearch("textShortFile.txt", "абракадабра");
        Assertions.assertEquals(new ArrayList<>(), result);
    }

    @Test
    void testSingleCharacterSubstring() throws RuntimeException {
        ArrayList<Long> result = SubstringSearch.resourceSearch("text.txt", "а");
        Assertions.assertEquals(result, new ArrayList<>(List.of(0L, 3L, 5L, 7L, 10L)));
    }

    @Test
    void testLargeFileSubstringSearch() throws IOException {
        Path tempFile = Files.createTempFile("large_test", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()))) {
            for (int i = 0; i < 9_000_000; ++i) {
                writer.write("a");
            }
            writer.write("aaaab");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Long> result;
        try (FileReader fileReader = new FileReader(tempFile.toFile())) {
            result = SubstringSearch.searchInReader(fileReader, "aaaab");
        }
        try {
            Assertions.assertEquals(List.of(8_999_996L), result);
        } finally {
            Files.delete(tempFile);
        }
    }

    @Test
    void testResourceSearch() {
        String resourceName = "test.txt";
        String subName = "ааааааааа";
        ArrayList<Long> result = SubstringSearch.resourceSearch(resourceName, subName);
        Assertions.assertEquals(result, List.of(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L));
    }
}