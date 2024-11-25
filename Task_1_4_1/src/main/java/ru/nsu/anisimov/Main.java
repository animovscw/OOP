package ru.nsu.anisimov;

import java.util.List;

public class Main {
    public static void main(String[] args) {
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

        System.out.println("Average score: " + gradeBook.calculateAverage());
        System.out.println("Can be transferred to the budget: " + gradeBook.canBeReplacedToBudget());

        gradeBook.setQualificationGrade(5);
        System.out.println("Can get red diploma: " + gradeBook.canGetRedDiploma());
        System.out.println("Can get increased scholarship: " + gradeBook.canGetIncreasedScholarship());
    }
}