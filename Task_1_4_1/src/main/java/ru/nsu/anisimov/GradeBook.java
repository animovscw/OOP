package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A grade book for a student, contains whatever he might want.
 */
public class GradeBook {
    private final List<List<GradeRecord>> semesters;
    private Grade qualificationGrade;
    private boolean qualificationCompleted;
    private final boolean isOnBudget;

    /**
     * Constructs a GradeBook with the budget status.
     *
     * @param isOnBudget whether the student is studying on a budget
     */
    public GradeBook(boolean isOnBudget) {
        this.semesters = new ArrayList<>();
        this.qualificationGrade = null;
        this.qualificationCompleted = false;
        this.isOnBudget = isOnBudget;
    }

    /**
     * Adds grades for a specific semester to the grade book.
     *
     * @param semesterGrades the list of grades for the semester
     */
    public void addSemesterGrades(List<GradeRecord> semesterGrades) {
        semesters.add(semesterGrades);
    }

    /**
     * Sets the qualification grade for the student.
     *
     * @param grade the qualification grade
     */
    public void setQualificationGrade(Grade grade) {
        this.qualificationGrade = grade;
        this.qualificationCompleted = true;
    }

    /**
     * Calculates the average grade from the last recorded grade for each subject.
     *
     * @return the average grade, or 0
     */
    public double calculateAverage() {
        int total = 0;
        int count = 0;
        Map<String, Grade> latest = getLastGradeForEachSubject();
        for (Grade grade : latest.values()) {
            total += grade.getValue();
            ++count;
        }
        if (count == 0) {
            return 0;
        } else {
            return (double) total / count;
        }
    }

    /**
     * Get the last grade for each subject across all semester.
     *
     * @return map of grades
     */
    private Map<String, Grade> getLastGradeForEachSubject() {
        return semesters.stream() // создает поток семестров
                .flatMap(List::stream) // объединяет потоки всех семестров в единый поток записей
                .collect(Collectors.toMap( // собирает элементы потока в Map
                        GradeRecord::getSubjectName, // КЛЮЧ - название предмета
                        GradeRecord::getGrade, // ЗНАЧЕНИЕ - оценка за предмет
                        (existing, replacement) -> replacement// если несколько
                        // записей, записывается последняя
                ));
    }

    /**
     * Determines if the student can transfer to budget.
     *
     * @return true if possible, false otherwise
     */
    public boolean canBeTransferredToBudget() {
        if (isOnBudget || semesters.size() < 2) {
            return false;
        }
        for (int i = semesters.size() - 2; i < semesters.size(); ++i) {
            for (GradeRecord record : semesters.get(i)) {
                if (record.isDifferentiated() && record.getGrade() == Grade.SATISFACTORY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines if the student is eligible for a red diploma in the future.
     *
     * @return true if possible, false otherwise
     */
    public boolean canGetRedDiploma() {
        if (!qualificationCompleted || qualificationGrade != Grade.EXCELLENT) {
            return false;
        }

        Map<String, Grade> latestGrades = getLastGradeForEachSubject();

        long totalSubjects = latestGrades.size();
        long excellentMarks = latestGrades.values().stream()
                .filter(grade -> grade == Grade.EXCELLENT)
                .count();

        boolean hasSatisfactoryGrade = latestGrades.values().stream()
                .anyMatch(grade -> grade == Grade.SATISFACTORY);

        if (hasSatisfactoryGrade) {
            return false;
        }

        return (double) (excellentMarks + (totalSubjects - excellentMarks)) / totalSubjects >= 0.75;
    }

    /**
     * Determines if the student can receive an increased scholarship.
     *
     * @return true if required all types, false otherwise
     */
    public boolean canGetIncreasedScholarship() {
        if (!isOnBudget || semesters.isEmpty()) {
            return false;
        }

        List<GradeRecord> lastSemester = semesters.get(semesters.size() - 1);
        for (GradeRecord record : lastSemester) {
            if (record.getGrade() != Grade.EXCELLENT) {
                return false;
            }
        }
        return true;
    }
}
