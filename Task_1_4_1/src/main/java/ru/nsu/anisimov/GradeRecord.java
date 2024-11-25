package ru.nsu.anisimov;

/**
 * Record of a student's grade in a subject for a semester.
 */
public class GradeRecord {
    private final String subjectName;
    private final Grade grade;
    private final boolean isDifferentiated;

    /**
     * Constructs a GradeRecord.
     * @param subjectName the name of the subject
     * @param grade the grade for the subject
     * @param isDifferentiated subject is graded with a differentiated grade
     */
    public GradeRecord(String subjectName, Grade grade, boolean isDifferentiated) {
        this.subjectName = subjectName;
        this.grade = grade;
        this.isDifferentiated = isDifferentiated;
    }

    /**
     * Gets the grade for the subject.
     *
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Checks if subject is graded with a differentiated grade.
     *
     * @return true if is differentiated, false otherwise
     */
    public boolean isDifferentiated() {
        return isDifferentiated;
    }

    /**
     * Gets the name of the subject.
     *
     * @return the name
     */
    public String getSubjectName() {
        return subjectName;
    }
}
