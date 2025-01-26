package org.example.lesson2.service;

import org.example.lesson2.model.Student;
import org.example.lesson2.model.StudentRegister;
import org.example.lesson2.model.Subjects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AddStudentToSubgroup extends HandlerSubgroupDecorator{
    public AddStudentToSubgroup(Handler subgroupHandler) {
        super(subgroupHandler);
    }

    @Override
    public HashSet<Student> findSubgroup(String subGroup, StudentRegister register) {

        HashSet<Student> newSubgroup=subgroupHandler.findSubgroup(subGroup,register);

        System.out.println("!!! Добавляем нового студента.");
        HashMap<Subjects, Integer> scoreCard1 = new HashMap<>();
        for (int j = 1; j < 10; j++) {
            scoreCard1.put(Subjects.values()[j], j%2==0?8:9);
        }
        newSubgroup.add(Student.builder()
                        .name("NEW STUDENT")
                        .group(78)
                        .scoreCard(scoreCard1)
                        .averageScore(8.5)
                        .build()
                );

        return newSubgroup;
    }

}
