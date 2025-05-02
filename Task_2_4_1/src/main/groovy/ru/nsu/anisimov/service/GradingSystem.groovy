package ru.nsu.anisimov.service

/**
 * Service for calculating final grades based on scores and activity.
 */
class GradingSystem {
    /**
     * Calculates the final letter grade considering both score and activity.
     *
     * @param totalScore Score
     * @param activityWeeks Number of weeks with Git activity
     * @return Letter grade
     */
    static String calculateGrade(int totalScore, int activityWeeks) {
        def baseGrade = calculateBaseGrade(totalScore)
        return adjustForActivity(baseGrade, activityWeeks)
    }

    /**
     * Calculates the base grade without activity adjustments.
     *
     * @param score Score (0-100)
     * @return Corresponding letter grade
     */
    private static String calculateBaseGrade(int score) {
        switch (score) {
            case 90..100: return "A"
            case 80..<90: return "B"
            case 70..<80: return "C"
            case 60..<70: return "D"
            default: return "F"
        }
    }

    /**
     * Adjusts the grade based on activity weeks.
     *
     * @param grade Letter grade
     * @param weeks Number of active weeks
     * @return Adjusted letter grade
     */
    private static String adjustForActivity(String grade, int weeks) {
        if (weeks < 5) {
            return grade == "A" ? "B" :
                    grade == "B" ? "C" :
                            grade == "C" ? "D" : grade
        } else if (weeks > 10) {
            return grade == "B" ? "A" :
                    grade == "C" ? "B" :
                            grade == "D" ? "C" : grade
        }
        return grade
    }
}