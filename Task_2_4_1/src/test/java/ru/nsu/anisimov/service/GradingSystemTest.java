package ru.nsu.anisimov.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GradingSystemTest {

    @Test
    void baseGrades() {
        assertEquals("A", GradingSystem.calculateGrade(95, 5));
        assertEquals("B", GradingSystem.calculateGrade(85, 5));
        assertEquals("C", GradingSystem.calculateGrade(75, 5));
        assertEquals("D", GradingSystem.calculateGrade(65, 5));
        assertEquals("F", GradingSystem.calculateGrade(50, 5));
    }

    @Test
    void activityAdjustments() {
        assertEquals("B", GradingSystem.calculateGrade(95, 3));
        assertEquals("A", GradingSystem.calculateGrade(85, 12));
    }
}
