package org.example.lesson2_UsePatterns.service;

import org.example.lesson2_UsePatterns.model.Student;
import org.example.lesson2_UsePatterns.model.StudentRegister;
import org.example.lesson2_UsePatterns.model.Subjects;
import org.example.lesson2_UsePatterns.transferTask.ManageStudent;
import org.example.lesson2_UsePatterns.transferTask.ManegeWithFailStudents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Использование паттерна Iterator, Template Method
 */
public class FailSubgroupHandlerSubgroup extends HandlerSubgroup {
    @Override
    public HashSet<Student> findSubgroup(String subGroup, StudentRegister register) {
        HashSet<Student> studentRegister=register.getStudentRegister();
        ManageStudent manegeStudents = new ManegeWithFailStudents();

        HashSet<Student> failStudent = new HashSet<>();
        if (subGroup.equals("fail")) {
            studentRegister.forEach(student -> {
                HashMap<Subjects,Integer> scoreCard=student.getScoreCard();

                /**
                 * Использование Iterator
                 */
                boolean lowMark = false;
                Iterator<Integer> iterator= scoreCard.values().iterator();
                while (iterator.hasNext()){
                    if (iterator.next()<4) lowMark=true;
                }

                if (lowMark || student.getAverageScore()<4) {
                    failStudent.add(student);
                }
            });

            /**
             * Применение паттерна Template Method
             */
            failStudent.forEach(student-> {
                manegeStudents.manegeWithStudent(register,student);
                System.out.println();
            });

        } else if (nextHandlerSubgroup != null) {
            nextHandlerSubgroup.findSubgroup(subGroup, register);
        }

        return failStudent;
    }
}
