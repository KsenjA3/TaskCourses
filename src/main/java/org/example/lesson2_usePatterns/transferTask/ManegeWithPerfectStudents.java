package org.example.lesson2_usePatterns.transferTask;

import org.example.lesson2_usePatterns.model.Student;

public class ManegeWithPerfectStudents extends ManageStudent {

    @Override
    protected void interactWithOtherDepartments() {
        System.out.println("Передача сведений о студенте в отдел кадров");
        System.out.println("Прием студента на работу");
    }

    @Override
    protected void interactWithStudents(Student student) {
        System.out.println("Offer job to "+ student.getName());
    }
}
