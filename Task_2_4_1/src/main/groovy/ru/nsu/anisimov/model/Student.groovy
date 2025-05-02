package ru.nsu.anisimov.model

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

/**
 * Represents a student participating in the course.
 */
@CompileStatic
@TupleConstructor
class Student {
    String name
    String github
    String repository
}