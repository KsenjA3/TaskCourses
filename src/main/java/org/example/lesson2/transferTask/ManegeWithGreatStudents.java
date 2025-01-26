package org.example.lesson2.transferTask;

import org.example.lesson2.model.Student;

public class ManegeWithGreatStudents extends ManageStudent {

    @Override
    protected void interactWithOtherDepartments() {
        System.out.println("Передача сведений о студенте в отдел стажировки");
        System.out.println("Зачисление студента на стажировку");
    }

    @Override
    protected void interactWithStudents(Student student) {
        System.out.println("Offer trainer to "+ student.getName());
    }
}
