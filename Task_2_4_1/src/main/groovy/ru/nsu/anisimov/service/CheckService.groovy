package ru.nsu.anisimov.service

import ru.nsu.anisimov.model.TestResult
import ru.nsu.anisimov.model.Task
import java.time.LocalDate

/**
 * Service for checking individual assignments.
 */
class CheckService {
    /**
     * Performs complete assignment verification.
     *
     * @param repoDir Repository directory containing the assignment
     * @param task Task
     * @return Map
     */
    static Map checkAssignment(File repoDir, Task task) {
        boolean compiled = compile(repoDir)
        boolean doc_ok = compiled ? generateJavadoc(repoDir) : false
        boolean style_ok = (compiled && doc_ok) ? checkStyle(repoDir) : false

        TestResult testResult = (compiled && style_ok)
                ? runTests(repoDir)
                : new TestResult(0, 0, 0)

        double score = calculateScore(
                task,
                compiled,
                doc_ok,
                style_ok,
                testResult,
                LocalDate.now()
        )

        return [
                compiled  : compiled,
                javadocOk : doc_ok,
                styleOk   : style_ok,
                testResult: testResult,
                score     : score
        ]
    }

    /**
     * Attempts to compile Java sources in the repository.
     *
     * @param repoDir Repository directory
     * @return True if compilation succeeded
     */
    static boolean compile(File repoDir) {
        def src = new File(repoDir, "src")
        if (!src.exists()) return false
        def cmd = ["javac", "-d", "${repoDir.absolutePath}/bin", "${src.absolutePath}/*.java"]
        return runCmd(cmd)
    }

    /**
     * Generates Javadoc for the repository.
     *
     * @param repoDir Repository directory
     * @return True if Javadoc generation succeeded
     */
    static boolean generateJavadoc(File repoDir) {
        def src = new File(repoDir, "src")
        if (!src.exists()) return false
        def docDir = new File(repoDir, "doc")
        docDir.mkdirs()
        def cmd = ["javadoc", "-d", docDir.absolutePath,
                   "-source path", src.absolutePath,
                   "-subpackages", "."]
        return runCmd(cmd)
    }

    /**
     * Runs Checkstyle on the repository.
     *
     * @param repoDir Repository directory
     * @return True if style check passed
     */
    static boolean checkStyle(File repoDir) {
        def config = new File("config/checkstyle.xml")
        if (!config.exists()) return false
        def cmd
        cmd = ["java", "-jar", "checkstyle.jar", "-c",
               config.absolutePath,
               "${repoDir.absolutePath}/src"]
        return runCmd(cmd)
    }

    /**
     * Runs JUnit tests in the repository.
     *
     * @param repoDir Repository directory
     * @return TestResult containing passed/failed/skipped counts
     */
    static TestResult runTests(File repoDir) {
        def cmd = [
                "java", "-jar", "junit-platform-console-standalone.jar",
                "--class-path", "${repoDir.absolutePath}/bin",
                "--scan-class-path"
        ]
        def proc = cmd.execute()
        def output = proc.in.text
        proc.waitFor()

        int passed = 0, failed = 0, skipped = 0
        def m = (output =~ /Tests run: (\d+), Failures: (\d+), Skipped: (\d+)/)
        if (m.find()) {
            int total = m.group(1).toInteger()
            failed = m.group(2).toInteger()
            skipped = m.group(3).toInteger()
            passed = total - failed - skipped
        }
        return new TestResult(passed, failed, skipped)
    }

    /**
     * Executes a shell command.
     * @param cmd Command
     * @return True if command succeeded
     */
    private static boolean runCmd(List<String> cmd) {
        try {
            def proc = new ProcessBuilder(cmd)
                    .redirectErrorStream(true)
                    .start()
            proc.waitFor()
            return proc.exitValue() == 0
        } catch (IOException ignored) {
            return false
        }
    }

    /**
     * Calculates the final score.
     *
     * @param task Task
     * @param compiled Whether compilation succeeded
     * @param javadocOk Whether Javadoc is correct
     * @param styleOk Whether style check passed
     * @param tr Test results
     * @param submissionDate Date when submitted
     * @return Final score
     */
    static double calculateScore(Task task,
                                 boolean compiled,
                                 boolean javadocOk,
                                 boolean styleOk,
                                 TestResult tr,
                                 LocalDate submissionDate) {
        double fraction = 0
        if (compiled) fraction += 0.30
        if (javadocOk) fraction += 0.10
        if (styleOk) fraction += 0.10

        int totalTests = tr.passed + tr.failed + tr.skipped
        if (totalTests > 0) {
            fraction += 0.50 * ((double) tr.passed / totalTests)
        }

        LocalDate soft = task.softDeadline
        LocalDate hard = task.hardDeadline
        if (submissionDate.isAfter(hard)) {
            fraction *= 0.70
        } else if (submissionDate.isAfter(soft)) {
            fraction *= 0.90
        }

        double raw = fraction * task.maxScore
        return Math.round(raw * 100) / 100.0
    }
}
