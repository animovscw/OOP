package ru.nsu.anisimov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubstringSearchTest {

    @Test
    void testMultipleOccurrences() throws IOException {
        ArrayList<Integer> result = SubstringSearch.resourceSearch("text.txt", "бра");
        Assertions.assertEquals(result, new ArrayList<>(List.of(1, 8)));
    }

    @Test
    void testNoOccurrences() throws IOException {
        ArrayList<Integer> result = SubstringSearch.resourceSearch("text.txt", "bra");
        Assertions.assertEquals(result, new ArrayList<>());
    }

    @Test
    void testEmptyFile() throws IOException {
        ArrayList<Integer> result = SubstringSearch.resourceSearch("textEmpty.txt", "abra");
        Assertions.assertEquals(result, new ArrayList<>());
    }

    @Test
    void testSubstringLongerThanFile() throws IOException {
        ArrayList<Integer> result = SubstringSearch.resourceSearch("textShortFile.txt", "абракадабра");
        Assertions.assertEquals(result, new ArrayList<>());
    }

    @Test
    void testSingleCharacterSubstring() throws IOException {
        ArrayList<Integer> result = SubstringSearch.resourceSearch("text.txt", "а");
        Assertions.assertEquals(result, new ArrayList<>(List.of(0, 3, 5, 7, 10)));
    }
}
