package org.example.lesson2.transferTask;

import org.example.lesson2.model.Student;
import org.example.lesson2.model.StudentRegister;


/**
 * Применение паттерна Template Method
 */
public abstract class ManageStudent {

    public final void manegeWithStudent(StudentRegister register, Student student){
        interactWithStudents(student);
        interactWithOtherDepartments();
        expelFromSchool (register, student);
    }

    protected abstract void interactWithOtherDepartments();
    protected abstract void interactWithStudents(Student student);

    private  void  expelFromSchool (StudentRegister register, Student student){
        System.out.println("Remove student from School");
        register.removeStudentFromSchool(student);
    }

}
