package ru.nsu.anisimov.service

import ru.nsu.anisimov.model.Student

/**
 * Service for Git repository operations.
 */
class GitService {
    /**
     * Clones or updates a student's repository.
     *
     * @param student Student
     * @param baseDir Base directory for cloning
     * @return File object
     */
    static File cloneRepository(Student student, String baseDir) {
        def repoDir = new File(baseDir, student.github)
        try {
            if (repoDir.exists()) {
                println ">>> Pulling ${student.github}"
                if (!runCommand(["git", "-C", repoDir.absolutePath, "pull"])) {
                    println "    [WARN] git pull failed for ${student.github}"
                    return null
                }
            } else {
                println ">>> Cloning ${student.github}"
                if (!runCommand(
                        ["git", "-C", baseDir, "clone", student.repository, student.github])
                ) {
                    println "    [WARN] git clone failed for ${student.github}"
                    return null
                }
            }

            ["main", "master"].find { branch ->
                runCommand(["git", "-C", repoDir.absolutePath, "checkout", branch])
            }

            return repoDir.exists() ? repoDir : null
        } catch (Exception e) {
            println "    [ERROR] GitService: ${e.message}"
            return null
        }
    }
    /**
     * Gets unique weeks when commits were made in the repository.
     *
     * @param repoDir Repository directory
     * @return List of week numbers with commit activity
     */
    static List<Integer> getCommitWeeks(File repoDir) {
        try {
            def proc = ["git", "-C", repoDir.absolutePath,
                        "log", "--pretty=format:%cd", "--date=short"].execute()
            def output = proc.in.text
            proc.waitFor()
            return output.readLines()
                    .collect { Date.parse('yyyy-MM-dd', it)[Calendar.WEEK_OF_YEAR as String] }
                    .unique() as List<Integer>
        } catch (Exception ignored) {
            return []
        }
    }

    /**
     * Executes a shell command.
     *
     * @param command Command and arguments as List
     * @return True if command succeeded
     */
    private static boolean runCommand(List<String> command) {
        def proc = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start()
        proc.waitFor()
        return proc.exitValue() == 0
    }
}
