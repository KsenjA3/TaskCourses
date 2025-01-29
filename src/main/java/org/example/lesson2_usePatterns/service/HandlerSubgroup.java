package org.example.lesson2_usePatterns.service;

import org.example.lesson2_usePatterns.model.Student;
import org.example.lesson2_usePatterns.model.StudentRegister;

import java.util.HashSet;


/**
 * Работа с разными подгруппами студентов
 * с применением паттерна  "Цепочка обязанностей" (Chain of Responsibility)
 */
public abstract class HandlerSubgroup implements Handler{
    protected HandlerSubgroup nextHandlerSubgroup;

    public void setNextHandler(HandlerSubgroup nextHandlerSubgroup) {
        this.nextHandlerSubgroup = nextHandlerSubgroup;
    }

    public abstract HashSet<Student> findSubgroup(String subGroup, StudentRegister register);
}
