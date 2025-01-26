package org.example.lesson2.service;

import org.example.lesson2.model.Student;
import org.example.lesson2.model.StudentRegister;

import java.util.HashSet;

public class PremiumForSubgroupStudents extends PremiumDecorator{
    public PremiumForSubgroupStudents(Handler subgroupHandler) {
        super(subgroupHandler);
    }

    @Override
    public HashSet<Student> findSubgroup(String subGroup, StudentRegister register) {
        System.out.println("!!! Назначить премию лучшим студентам.");
        //code передачи списка HashSet<Student> list = subgroupHandler.findSubgroup(subGroup,register) в бухгалтерию

        return subgroupHandler.findSubgroup(subGroup,register);
    }
}
