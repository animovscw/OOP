package ru.nsu.anisimov.model

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import java.time.LocalDate

/**
 * Represents a programming task with evaluation criteria and deadlines.
 */
@CompileStatic
@TupleConstructor
class Task {
    String id
    String name
    int maxScore
    LocalDate softDeadline
    LocalDate hardDeadline
}