package org.example.lesson2_usePatterns.service;

import org.example.lesson2_usePatterns.model.Student;
import org.example.lesson2_usePatterns.model.StudentRegister;
import org.example.lesson2_usePatterns.model.Subjects;
import org.example.lesson2_usePatterns.transferTask.ManageStudent;
import org.example.lesson2_usePatterns.transferTask.ManegeWithPerfectStudents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Использование паттерна Iterator, Template Method
 */
public class PerfectSubgroupHandlerSubgroup extends HandlerSubgroup {
    @Override
    public HashSet<Student> findSubgroup(String subGroup, StudentRegister register) {
        HashSet<Student> studentRegister=register.getStudentRegister();
        ManageStudent manegeStudents = new ManegeWithPerfectStudents();

        HashSet<Student> perfectStudent = new HashSet<>();
        if (subGroup.equals("perfect")) {
            studentRegister.forEach(student -> {
                HashMap<Subjects,Integer> scoreCard=student.getScoreCard();

                /**
                 * Использование Iterator
                 */
                boolean lowMark = false;
                Iterator<Integer> iterator= scoreCard.values().iterator();
                while (iterator.hasNext()){
                    if (iterator.next()<8) lowMark=true;
                }

                if (!lowMark && student.getAverageScore()>=8) {
                    perfectStudent.add(student);
                }
            });

            /**
             * Применение паттерна Template Method
             */
            perfectStudent.forEach(student-> {
                manegeStudents.manegeWithStudent(register,student);
                System.out.println();
            });

        } else if (nextHandlerSubgroup != null) {
             nextHandlerSubgroup.findSubgroup(subGroup, register);
        }
        return perfectStudent;
    }
}
