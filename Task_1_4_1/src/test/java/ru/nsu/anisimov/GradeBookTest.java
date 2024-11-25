package ru.nsu.anisimov;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class GradeBookTest {
    @Test
    void testAverageGrade() {
        GradeBook gradeBook = new GradeBook(false);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", Grade.EXCELLENT, false),
                new GradeRecord("Введение в операционные системы", Grade.SATISFACTORY, true),
                new GradeRecord("Объектно ориентированное программирование", Grade.EXCELLENT, true)
        );
        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", Grade.GOOD, false),
                new GradeRecord("Объектно ориентированное программирование",
                        Grade.EXCELLENT, false),
                new GradeRecord("Теория вероятности", Grade.EXCELLENT, false)
        );
        gradeBook.addSemesterGrades(sem2);

        Assertions.assertEquals(4.75, gradeBook.calculateAverage());
    }

    @Test
    void testTransferToBudget() {
        GradeBook gradeBook = new GradeBook(false);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", Grade.EXCELLENT, false),
                new GradeRecord("Введение в операционные системы", Grade.GOOD, true),
                new GradeRecord("Объектно ориентированное программирование", Grade.EXCELLENT, true)
        );
        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", Grade.GOOD, false),
                new GradeRecord("Объектно ориентированное программирование",
                        Grade.EXCELLENT, false),
                new GradeRecord("Теория вероятности", Grade.EXCELLENT, false)
        );
        gradeBook.addSemesterGrades(sem2);

        Assertions.assertTrue(gradeBook.canBeTransferredToBudget());
    }

    @Test
    void testNoTransferToBudget() {
        GradeBook gradeBook = new GradeBook(false);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", Grade.EXCELLENT, false),
                new GradeRecord("Введение в операционные системы", Grade.SATISFACTORY, true),
                new GradeRecord("Объектно ориентированное программирование", Grade.EXCELLENT, true)
        );
        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", Grade.GOOD, false),
                new GradeRecord("Объектно ориентированное программирование", Grade.GOOD, false),
                new GradeRecord("Теория вероятности", Grade.EXCELLENT, false)
        );
        gradeBook.addSemesterGrades(sem2);

        Assertions.assertFalse(gradeBook.canBeTransferredToBudget());
    }

    @Test
    void testNoQualificationNoDiploma() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", Grade.EXCELLENT, false),
                new GradeRecord("Введение в операционные системы", Grade.EXCELLENT, true),
                new GradeRecord("Объектно ориентированное программирование", Grade.EXCELLENT, true)
        );
        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", Grade.EXCELLENT, false),
                new GradeRecord("Объектно ориентированное программирование", Grade.GOOD, false),
                new GradeRecord("Теория вероятности", Grade.EXCELLENT, false)
        );
        gradeBook.addSemesterGrades(sem2);

        Assertions.assertFalse(gradeBook.canGetRedDiploma());
    }

    @Test
    void testNoLuckNoDiploma() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", Grade.EXCELLENT, false),
                new GradeRecord("Введение в операционные системы", Grade.EXCELLENT, true),
                new GradeRecord("Объектно ориентированное программирование", Grade.EXCELLENT, true)
        );
        gradeBook.addSemesterGrades(sem1);

        List<GradeRecord> sem2 = List.of(
                new GradeRecord("Введение в операционные системы", Grade.EXCELLENT, false),
                new GradeRecord("Объектно ориентированное программирование",
                        Grade.SATISFACTORY, false),
                new GradeRecord("Теория вероятности", Grade.EXCELLENT, false)
        );
        gradeBook.addSemesterGrades(sem2);
        gradeBook.setQualificationGrade(Grade.EXCELLENT);

        Assertions.assertFalse(gradeBook.canGetRedDiploma());
    }

    @Test
    void testNoBrainNoMoney() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", Grade.EXCELLENT, false),
                new GradeRecord("Введение в операционные системы", Grade.UNSATISFACTORY, true),
                new GradeRecord("Объектно ориентированное программирование", Grade.EXCELLENT, true)
        );
        gradeBook.addSemesterGrades(sem1);

        Assertions.assertFalse(gradeBook.canGetIncreasedScholarship());
    }

    @Test
    void testNoBrainMuchLuck() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Дифференциальные уравнения", Grade.EXCELLENT, false),
                new GradeRecord("Введение в операционные системы", Grade.EXCELLENT, true),
                new GradeRecord("Объектно ориентированное программирование", Grade.EXCELLENT, true)
        );
        gradeBook.addSemesterGrades(sem1);

        Assertions.assertTrue(gradeBook.canGetIncreasedScholarship());
    }

    @Test
    void testCanGetRedDiplomaFuture() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("ОРГ", Grade.EXCELLENT, true),
                new GradeRecord("Разработка ПАК", Grade.GOOD, true)
        );
        gradeBook.addSemesterGrades(sem1);
        gradeBook.setQualificationGrade(Grade.EXCELLENT);

        Assertions.assertTrue(gradeBook.canGetRedDiploma());
    }

    @Test
    void testCannotGetRedDiplomaDueToSatisfactory() {
        GradeBook gradeBook = new GradeBook(true);

        List<GradeRecord> sem1 = List.of(
                new GradeRecord("Физическая культура", Grade.EXCELLENT, true),
                new GradeRecord("История России", Grade.SATISFACTORY, true)
        );
        gradeBook.addSemesterGrades(sem1);
        gradeBook.setQualificationGrade(Grade.EXCELLENT);

        Assertions.assertFalse(gradeBook.canGetRedDiploma());
    }
}