package org.example.lesson2_usePatterns.service;

import org.example.lesson2_usePatterns.model.Student;
import org.example.lesson2_usePatterns.model.StudentRegister;
import org.example.lesson2_usePatterns.model.Subjects;
import org.example.lesson2_usePatterns.transferTask.ManageStudent;
import org.example.lesson2_usePatterns.transferTask.ManegeWithGreatStudents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Использование паттерна Iterator, Template Method
 */
public class GreatSubgroupHandlerSubgroup extends HandlerSubgroup {
    @Override
    public HashSet<Student> findSubgroup(String subGroup, StudentRegister register) {
        HashSet<Student> studentRegister=register.getStudentRegister();
        ManageStudent manegeStudents = new ManegeWithGreatStudents();

        HashSet<Student> greatStudent = new HashSet<>();
        if (subGroup.equals("great")) {
            studentRegister.forEach(student -> {
                HashMap<Subjects,Integer> scoreCard=student.getScoreCard();

                /**
                 * Использование Iterator
                 */
                Iterator<Integer> iterator= scoreCard.values().iterator();
                boolean lowMark = false;
                while (iterator.hasNext()){
                    if (iterator.next()<6) lowMark=true;
                }

                if (!lowMark && (student.getAverageScore()>7 && student.getAverageScore()<8) ) {
                    greatStudent.add(student);
                }
            });

            /**
             * Применение паттерна Template Method
             */
            greatStudent.forEach(student-> {
                manegeStudents.manegeWithStudent(register,student);
                System.out.println();
            });

        } else if (nextHandlerSubgroup != null) {
            nextHandlerSubgroup.findSubgroup(subGroup, register);
        }
        return greatStudent;
    }
}
