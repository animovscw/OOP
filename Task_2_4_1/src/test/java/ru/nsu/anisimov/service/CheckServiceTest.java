package ru.nsu.anisimov.service;

import org.junit.jupiter.api.Test;
import ru.nsu.anisimov.model.TestResult;
import ru.nsu.anisimov.model.Task;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class CheckServiceTest {

    @Test
    void calculateScoreBeforeSoftDeadline_noTests() {
        Task task = new Task("id", "name", 10,
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(10));

        double score = CheckService.calculateScore(
                task,
                true,
                true,
                true,
                new TestResult(0, 0, 0),
                LocalDate.now()  // перед soft
        );
        assertEquals(0.5 * 10, score, 1e-6);
    }

    @Test
    void calculateScoreBetweenSoftAndHard_withTests() {
        Task task = new Task("id", "name", 20,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(5));

        TestResult tr = new TestResult(5, 5, 0);

        double score = CheckService.calculateScore(
                task,
                true, true, true,
                tr,
                LocalDate.now());

        double expected = 0.75 * 20 * 0.9;
        assertEquals(Math.round(expected * 100) / 100.0, score, 1e-6);
    }

    @Test
    void calculateScoreAfterHardDeadline() {
        Task task = new Task("id", "n", 30,
                LocalDate.now().minusDays(10),
                LocalDate.now().minusDays(1));

        double score = CheckService.calculateScore(
                task,
                false, false, false,
                new TestResult(0, 0, 0),
                LocalDate.now()  // после hard
        );
        assertEquals(0.0, score, 1e-6);
    }
}
