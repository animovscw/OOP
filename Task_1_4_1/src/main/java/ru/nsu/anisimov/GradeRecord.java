package ru.nsu.anisimov;

public class GradeRecord {
    private final String subjectName;
    private final int grade;
    private final boolean isDifferentiated;

    public GradeRecord(String subjectName, int grade, boolean isDifferentiated) {
        this.subjectName = subjectName;
        this.grade = grade;
        this.isDifferentiated = isDifferentiated;
    }

    public int getGrade() {
        return grade;
    }

    public boolean isDifferentiated() {
        return isDifferentiated;
    }
}
