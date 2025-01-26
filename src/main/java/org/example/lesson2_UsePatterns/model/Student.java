package org.example.lesson2_UsePatterns.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Builder
@ToString
@Getter
@Setter
public class Student {
    private String name;
    private int group;
    private double averageScore;
    private HashMap<Subjects,Integer> scoreCard;
}
