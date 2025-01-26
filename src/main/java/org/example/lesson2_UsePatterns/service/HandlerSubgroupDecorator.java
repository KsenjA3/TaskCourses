package org.example.lesson2_UsePatterns.service;

import lombok.AllArgsConstructor;
import org.example.lesson2_UsePatterns.model.Student;
import org.example.lesson2_UsePatterns.model.StudentRegister;

import java.util.HashSet;

@AllArgsConstructor
public abstract class HandlerSubgroupDecorator implements Handler{
    protected Handler subgroupHandler;

    @Override
    public HashSet<Student> findSubgroup(String subGroup, StudentRegister register) {
        return subgroupHandler.findSubgroup(subGroup,register);
    }
}
