package org.example.lesson2.service;

import org.example.lesson2.model.Student;
import org.example.lesson2.model.StudentRegister;

import java.util.HashSet;

public interface Handler {
    public  HashSet<Student> findSubgroup(String subGroup, StudentRegister register);
}
