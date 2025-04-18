package ru.nsu.anisimov.model;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

public class Group {
    public String name;
    public List<Student> students = new ArrayList<>();

    public void student(Closure<?> cl) {
        var st = new Student();
        cl.setDelegate(st);
        cl.call();
        students.add(st);
    }

    public List<Student> getStudents() {
        return students;
    }
}
