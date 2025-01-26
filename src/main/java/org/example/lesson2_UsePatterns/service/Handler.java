package org.example.lesson2_UsePatterns.service;

import org.example.lesson2_UsePatterns.model.Student;
import org.example.lesson2_UsePatterns.model.StudentRegister;

import java.util.HashSet;

public interface Handler {
    public  HashSet<Student> findSubgroup(String subGroup, StudentRegister register);
}
