package ru.nsu.anisimov.model

import groovy.transform.ToString

/**
 * Represents a student's assignment submission with all verification results.
 */
@ToString(includeNames = true, includePackage = false)
class Assignment {
    Student student
    Task task

    double score
    boolean compiled
    boolean javadocOk
    boolean styleOk

    boolean testsPassed

    int passed
    int failed
    int skipped

    /**
     * Default constructor allowing named parameters initialization.
     */
    Assignment() {}
}
