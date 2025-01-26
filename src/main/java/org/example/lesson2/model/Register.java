package org.example.lesson2.model;

import java.util.HashMap;

public interface Register {
    public void includeStudentToShcool (String name, int group, HashMap<Subjects,Integer> scoreCard);
    public void removeStudentFromSchool (Student student);
    public void updateStudent (Student student, Subjects subject, Integer mark);
}
