package org.example.lesson2_UsePatterns.transferTask;

import org.example.lesson2_UsePatterns.model.Student;

public class ManegeWithFailStudents extends ManageStudent {

    @Override
    protected void interactWithOtherDepartments() {
        System.out.println("Передача сведений об отчислении студента");
    }

    @Override
    protected void interactWithStudents(Student student) {
        System.out.println("Информирование "+ student.getName() +" об отчислении.");
    }
}
