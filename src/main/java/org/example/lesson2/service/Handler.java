package org.example.lesson2.service;

import org.example.lesson2.model.Student;
import org.example.lesson2.model.StudentRegister;

import java.util.HashSet;


/**
 * Работа с разными подгруппами студентов
 * с применением паттерна  "Цепочка обязанностей" (Chain of Responsibility)
 */
public abstract class Handler {
    protected Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract HashSet<Student> findSubgroup(String subGroup, StudentRegister register);
}
