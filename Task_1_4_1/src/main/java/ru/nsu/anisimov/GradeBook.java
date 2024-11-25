package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.List;

public class GradeBook {
    private final List<List<GradeRecord>> semesters;
    private int qualificationGrade;
    private boolean qualificationCompleted;
    private final boolean isOnBudget;

    public GradeBook(boolean isOnBudget) {
        this.semesters = new ArrayList<>();
        this.qualificationGrade = 0;
        this.qualificationCompleted = false;
        this.isOnBudget = isOnBudget;
    }

    public void addSemesterGrades(List<GradeRecord> semesterGrades) {
        semesters.add(semesterGrades);
    }

    public void setQualificationGrade(int grade) {
        this.qualificationGrade = grade;
        this.qualificationCompleted = true;
    }

    public double calculateAverage() {
        int count = 0;
        int total = 0;
        for (List<GradeRecord> semester : semesters) {
            for (GradeRecord record : semester) {
                total += record.getGrade();
                ++count;
            }
        }
        if (count == 0) {
            return 0;
        } else {
            return (double) total / count;
        }
    }

    public boolean canBeReplacedToBudget() {
        if (isOnBudget || semesters.size() < 2) {
            return false;
        }
        for (int i = semesters.size() - 2; i < semesters.size(); ++i) {
            for (GradeRecord record : semesters.get(i)) {
                if (record.isDifferentiated() && record.getGrade() == 3) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canGetRedDiploma() {
        if (!qualificationCompleted || qualificationGrade != 5) {
            return false;
        }
        int countExcellentMarks = 0;
        int total = 0;
        for (List<GradeRecord> semester : semesters) {
            for (GradeRecord record : semester) {
                if (record.getGrade() == 5) {
                    ++countExcellentMarks;
                }
                if (record.getGrade() == 3) {
                    return false;
                }
                ++total;
            }
        }
        return countExcellentMarks >= total * 0.75;
    }

    public boolean canGetIncreasedScholarship() {
        if (!isOnBudget || semesters.isEmpty()) {
            return false;
        }

        List<GradeRecord> lastSemester = semesters.get(semesters.size() - 1);
        for (GradeRecord record : lastSemester) {
            if (record.getGrade() != 5) {
                return false;
            }
        }
        return true;
    }
}
