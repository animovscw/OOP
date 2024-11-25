package ru.nsu.anisimov;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

class GradeBookTest {
    @Test
    void testAverageGrade() {
        GradeBook gradeBook = new GradeBook(false);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", 5, false),
                new GradeRecord("Введение в операционные системы", 3, true),
                new GradeRecord("Объектно ориентированное программирование", 5, true)
        );

        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", 4, false),
                new GradeRecord("Объектно ориентированное программирование", 5, false),
                new GradeRecord("Теория вероятности", 5, false)
        );

        gradeBook.addSemesterGrades(sem2);
        Assertions.assertEquals(4.5, gradeBook.calculateAverage());
    }

    @Test
    void testTransferToBudget() {
        GradeBook gradeBook = new GradeBook(false);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", 5, false),
                new GradeRecord("Введение в операционные системы", 4, true),
                new GradeRecord("Объектно ориентированное программирование", 5, true)
        );

        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", 4, false),
                new GradeRecord("Объектно ориентированное программирование", 5, false),
                new GradeRecord("Теория вероятности", 5, false)
        );

        gradeBook.addSemesterGrades(sem2);

        Assertions.assertTrue(gradeBook.canBeReplacedToBudget());
    }

    @Test
    void testNoTransferToBudget() {
        GradeBook gradeBook = new GradeBook(false);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", 5, false),
                new GradeRecord("Введение в операционные системы", 3, true),
                new GradeRecord("Объектно ориентированное программирование", 5, true)
        );

        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", 4, false),
                new GradeRecord("Объектно ориентированное программирование", 5, false),
                new GradeRecord("Теория вероятности", 5, false)
        );

        gradeBook.addSemesterGrades(sem2);
        Assertions.assertFalse(gradeBook.canBeReplacedToBudget());
    }

    @Test
    void testCanGetRedDiploma() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", 5, false),
                new GradeRecord("Введение в операционные системы", 5, true),
                new GradeRecord("Объектно ориентированное программирование", 5, true)
        );

        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", 5, false),
                new GradeRecord("Объектно ориентированное программирование", 4, false),
                new GradeRecord("Теория вероятности", 5, false)
        );

        gradeBook.addSemesterGrades(sem2);
        gradeBook.setQualificationGrade(5);
        Assertions.assertTrue(gradeBook.canGetRedDiploma());
    }

    @Test
    void testNoQualificationNoDiploma() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", 5, false),
                new GradeRecord("Введение в операционные системы", 5, true),
                new GradeRecord("Объектно ориентированное программирование", 5, true)
        );

        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", 5, false),
                new GradeRecord("Объектно ориентированное программирование", 4, false),
                new GradeRecord("Теория вероятности", 5, false)
        );

        gradeBook.addSemesterGrades(sem2);
        Assertions.assertFalse(gradeBook.canGetRedDiploma());
    }

    @Test
    void testNoLuckNoDiploma() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", 5, false),
                new GradeRecord("Введение в операционные системы", 5, true),
                new GradeRecord("Объектно ориентированное программирование", 5, true)
        );

        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", 5, false),
                new GradeRecord("Объектно ориентированное программирование", 3, false),
                new GradeRecord("Теория вероятности", 5, false)
        );

        gradeBook.addSemesterGrades(sem2);
        gradeBook.setQualificationGrade(5);
        Assertions.assertFalse(gradeBook.canGetRedDiploma());
    }

    @Test
    void testNoBrainNoMoney() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", 5, false),
                new GradeRecord("Введение в операционные системы", 2, true),
                new GradeRecord("Объектно ориентированное программирование", 5, true)
        );

        gradeBook.addSemesterGrades(sem1);

        Assertions.assertFalse(gradeBook.canGetIncreasedScholarship());
    }

    @Test
    void testNoBrainMuchLuck() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", 5, false),
                new GradeRecord("Введение в операционные системы", 5, true),
                new GradeRecord("Объектно ориентированное программирование", 5, true)
        );

        gradeBook.addSemesterGrades(sem1);

        Assertions.assertTrue(gradeBook.canGetIncreasedScholarship());
    }
}