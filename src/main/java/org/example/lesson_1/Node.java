package org.example.lesson_1;

import lombok.Data;

@Data
public class Node <T>{
    private T value;
    private Node next;
    private Node previous;
}
