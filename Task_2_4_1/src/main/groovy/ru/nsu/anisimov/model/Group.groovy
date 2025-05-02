package ru.nsu.anisimov.model

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

/**
 * Represents a student group containing multiple students.
 */
@CompileStatic
@TupleConstructor
class Group {
    String name
    List<Student> students
}