package ru.nsu.anisimov.service

import org.jsoup.Jsoup
import java.nio.file.*

/**
 * Service for generating HTML reports of student results.
 */
class ReportService {
    /**
     * Generates a comprehensive HTML report.
     *
     * @param params Map
     */
    static void generateFullReport(Map params) {
        def doc = Jsoup.parse("""
            <html>
                <head>
                    <title>OOP Lab Report</title>
                    <style>
                        table { border-collapse: collapse; width: 100%; }
                        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                        th { background-color: #f2f2f2; }
                        .passed { color: green; }
                        .failed { color: red; }
                    </style>
                </head>
                <body>
                    <h1>OOP Lab Report</h1>
                    <div id="content"></div>
                </body>
            </html>
        """)

        def content = doc.getElementById("content")

        params.groups.each { group ->
            content.appendElement("h2").text("Group: ${group.name}")

            params.tasks.each { task ->
                def assignments = params.results.findAll {
                    it.task.id == task.id && it.student in group.students
                }

                if (assignments) {
                    content.appendElement("h3").text("Task: ${task.name} (${task.id})")

                    def table = content.appendElement("table")
                    def header = table.appendElement("tr")
                    header.appendElement("th").text("Student")
                    header.appendElement("th").text("Build")
                    header.appendElement("th").text("Style")
                    header.appendElement("th").text("Tests")
                    header.appendElement("th").text("Score")

                    assignments.each { a ->
                        def row = table.appendElement("tr")
                        row.appendElement("td").text(a.student.name)
                        row.appendElement("td")
                                .text(a.compiled ? "✓" : "✗")
                                .addClass(a.compiled ? "passed" : "failed")
                        row.appendElement("td")
                                .text(a.styleOk ? "✓" : "✗")
                                .addClass(a.styleOk ? "passed" : "failed")
                        row.appendElement("td")
                                .text(a.testsPassed ? "✓" : "✗")
                                .addClass(a.testsPassed ? "passed" : "failed")
                        row.appendElement("td").text("${a.score}/${task.maxScore}")
                    }
                }
            }
        }

        content.appendElement("h2").text("Checkpoints Summary")
        params.checkpoints.each { cp ->
            def cpSection = content.appendElement("div")
            cpSection.appendElement("h3").text(cp.name)

            def cpTable = cpSection.appendElement("table")
            def header = cpTable.appendElement("tr")
            header.appendElement("th").text("Student")
            header.appendElement("th").text("Total Score")
            header.appendElement("th").text("Activity Weeks")
            header.appendElement("th").text("Grade")

            params.groups.each { group ->
                group.students.each { student ->
                    def totalDouble = params.results
                            .findAll { it.student == student }
                            .sum { it.score as Double } ?: 0.0
                    int totalScore = totalDouble.toInteger()

                    def activity = params.activity[student] ?: 0
                    def grade = GradingSystem.calculateGrade(totalScore, activity as int)

                    def row = cpTable.appendElement("tr")
                    row.appendElement("td").text(student.name)
                    row.appendElement("td").text(totalScore.toString())
                    row.appendElement("td").text("${activity} weeks")
                    row.appendElement("td").text(grade)
                }
            }
        }

        def reportDir = Paths.get("reports")
        Files.createDirectories(reportDir)
        def reportFile = reportDir.resolve("report-${System.currentTimeMillis()}.html")
        Files.writeString(reportFile, doc.toString())
        println "Report generated: ${reportFile.toAbsolutePath()}"
    }
}
