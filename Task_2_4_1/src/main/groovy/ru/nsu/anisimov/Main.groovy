package ru.nsu.anisimov

import ru.nsu.anisimov.model.*
import ru.nsu.anisimov.service.*
import java.time.LocalDate

/**
 * Main application class for the grading system.
 */
class Main {
/**
 * Main entry point.
 *
 * @param args Command line arguments
 */
    static void main(String[] args) {
        try {
            def configDir = args ? args[0] : 'config'
            println "Loading configuration from: ${new File(configDir).absolutePath}"

            def tasksCfg = ConfigLoader.loadConfig("${configDir}/tasks.groovy")
            def groupsCfg = ConfigLoader.loadConfig("${configDir}/groups.groovy")
            def semesterCfg = ConfigLoader.loadConfig("${configDir}/semester.groovy")

            List<Task> tasks = tasksCfg.tasks.collect { t ->
                new Task(
                        t.id,
                        t.name,
                        t.maxScore as Integer,
                        LocalDate.parse(t.softDeadline as String),
                        LocalDate.parse(t.hardDeadline as String)
                )
            }

            List<Group> groups = groupsCfg.groups.collect { g ->
                new Group(
                        g.name as String,
                        g.students.collect { s ->
                            new Student(
                                    s.name as String,
                                    s.github as String,
                                    s.repository as String
                            )
                        }
                )
            }

            def results = []
            def activityMap = [:]

            def baseDir = new File('repos')
            baseDir.mkdirs()

            groups.each { group ->
                println "\nProcessing group: ${group.name}"
                group.students.each { student ->
                    println "  Student: ${student.name} (${student.github})"

                    def repoDir = GitService.cloneRepository(student, baseDir.path)
                    if (!repoDir) {
                        handleCloneError(
                                student, tasks, semesterCfg.checkAssignments as List<Map>, results
                        )
                        return
                    }

                    def weeks = GitService.getCommitWeeks(repoDir)
                    activityMap[student] = weeks.size()

                    semesterCfg.checkAssignments.each { asg ->
                        if (student.github in (asg.students as List)) {
                            def task = tasks.find { it.id == asg.taskId }
                            if (!task) {
                                println "    [WARN] Task ${asg.taskId} not found"
                                return
                            }
                            println "    Checking ${task.id}: ${task.name}"

                            def res = CheckService.checkAssignment(repoDir, task)
                            TestResult tr = res.testResult as TestResult

                            results << new Assignment(
                                    student: student,
                                    task: task,
                                    score: res.score as Double,
                                    compiled: res.compiled as Boolean,
                                    javadocOk: res.javadocOk as Boolean,
                                    styleOk: res.styleOk as Boolean,
                                    testsPassed: (tr.passed > 0),
                                    passed: tr.passed,
                                    failed: tr.failed,
                                    skipped: tr.skipped
                            )
                        }
                    }
                }
            }

            semesterCfg.bonuses.each { bonus ->
                results.findAll {
                    it.student.github == bonus.studentId && it.task.id == bonus.taskId
                }
                        .each { it.score += bonus.points as Double }
            }

            println "\nGenerating report..."
            ReportService.generateFullReport(
                    groups: groups,
                    tasks: tasks,
                    results: results,
                    checkpoints: semesterCfg.checkpoints,
                    activity: activityMap,
                    bonuses: semesterCfg.bonuses
            )
            println "Done! Reports are in 'reports/'"
        }
        catch (Exception e) {
            println "[FATAL] ${e.message}"
            e.printStackTrace()
            System.exit(1)
        }
    }
/**
 * Handles repository clone errors by creating zero-score assignments.
 *
 * @param student The student whose repository couldn't be cloned
 * @param tasks List of tasks
 * @param assignments List of assignment configurations
 * @param results List to store the resulting zero-score assignments
 */
    private static void handleCloneError(Student student, List<Task> tasks,
                                         List<Map> assignments, List<Assignment> results) {
        println "    [WARNING] cannot access ${student.github}, creating empty records"
        assignments.findAll { it.students.contains(student.github) }
                .each { asg ->
                    def t = tasks.find { it.id == asg.taskId }
                    if (t) {
                        results << new Assignment(
                                student: student,
                                task: t,
                                score: 0.0,
                                compiled: false,
                                javadocOk: false,
                                styleOk: false,
                                testsPassed: false,
                                passed: 0,
                                failed: 0,
                                skipped: 0
                        )
                    }
                }
    }
}
