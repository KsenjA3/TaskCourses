package org.example.lesson2_usePatterns.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.HashSet;

/**
 * в классе ведется список студентов
 * ответственность - создание и удаление записей о студентах
 * а также изменение оценки, допустим при перездаче
 * с применением паттерна Builder
 */
@Getter
@Setter
@ToString
public class StudentRegister implements Register {
    private HashSet<Student> studentRegister;

    public StudentRegister() {
        studentRegister = new HashSet<>();
    }

    private void addStudent( Student student) {
        System.out.println(student.getName() + " принят в School.");
        studentRegister.add(student);
    }
    private void removeStudent( Student student) {
        System.out.println(student.getName() + " отчислен из School.");
        studentRegister.remove(student);
    }

/**
use pattern Builder throw library Lombok
 */
    public void includeStudentToShcool (String name, int group,
                                HashMap<Subjects,Integer> scoreCard) {

        double averageScore = scoreCard.values().stream()
                .mapToInt(i->i)
                .average()
                .getAsDouble();

        Student student =Student.builder()
                .name(name)
                .group(group)
                .averageScore(Math.ceil(averageScore*100)/100)
                .scoreCard(scoreCard)
                .build();

        addStudent(student);
    }

    public void removeStudentFromSchool (Student student) {
        removeStudent(student);
    }


    public void updateStudent (Student student, Subjects subject, Integer mark) {
        HashMap<Subjects,Integer> scoreCard=student.getScoreCard();
        scoreCard.computeIfAbsent(subject,v->mark);
        student.setScoreCard(scoreCard);

        double averageScore = scoreCard.values().stream()
                .mapToInt(i->i)
                .average()
                .getAsDouble();
        student.setAverageScore(Math.round(averageScore*100)/100);

        System.out.println(student.getName() + " перездал экзамен.");
    }
}
