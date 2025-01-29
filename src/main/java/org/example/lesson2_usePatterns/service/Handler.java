package org.example.lesson2_usePatterns.service;

import org.example.lesson2_usePatterns.model.Student;
import org.example.lesson2_usePatterns.model.StudentRegister;

import java.util.HashSet;

public interface Handler {
    public  HashSet<Student> findSubgroup(String subGroup, StudentRegister register);
}
