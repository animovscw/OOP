package ru.nsu.anisimov;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ru.nsu.anisimov.model.Course;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("---     Сборка данных    ---");

        Course course = new Course();
        Binding binding = new Binding();
        binding.setVariable("course", new CourseBuilder(course));

        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new File("checker.groovy"));

        System.out.println("- Загружено задач: " + course.getTasks().size());
        System.out.println("- Загружено студентов: " + course.getAllStudents().size());
    }
}
