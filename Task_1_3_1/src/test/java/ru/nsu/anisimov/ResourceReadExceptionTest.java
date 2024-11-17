package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResourceReadExceptionTest {
    @Test
    void testResourceNotFound() {
        String resourceName = "nonexistent.txt";
        String subName = "test";

        ResourceReadException exception = Assertions.assertThrows(
                ResourceReadException.class,
                () -> SubstringSearch.resourceSearch(resourceName, subName)
        );

        Assertions.assertTrue(exception.getMessage().contains("Resource not found"));
    }
}