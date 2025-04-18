package ru.nsu.anisimov

import ru.nsu.anisimov.model.*;

class CourseBuilder {
    Course course

    CourseBuilder(Course course) {
        this.course = course;
    }

    def task(Closure cl) {
        def task = new Task()
        cl.delegate = task
        cl.call()
        course.tasks.add(task)
    }

    def group(String groupName, Closure cl) {
        def group = new Group(name: groupName)
        cl.delegate = group
        cl.call()
        course.groups.add(group)
    }
}