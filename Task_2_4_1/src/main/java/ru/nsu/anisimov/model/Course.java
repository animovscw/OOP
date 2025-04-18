package ru.nsu.anisimov.model;

import java.util.*;

public class Course {
    public List<Task> tasks = new ArrayList<>();
    public List<Group> groups = new ArrayList<>();

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Student> getAllStudents() {
        List<Student> result = new ArrayList<>();
        for (Group group : groups) {
            result.addAll(group.getStudents());
        }
        return result;
    }
}
