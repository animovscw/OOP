package ru.nsu.anisimov.model

import groovy.transform.TupleConstructor
import groovy.transform.ToString

/**
 * Contains results of test execution for an assignment.
 */
@TupleConstructor
@ToString(includeNames = true)
class TestResult {
    int passed
    int failed
    int skipped
}
