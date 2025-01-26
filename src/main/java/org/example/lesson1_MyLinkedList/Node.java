package org.example.lesson1_MyLinkedList;

import lombok.Data;

@Data
public class Node <T>{
    private T value;
    private Node next;
    private Node previous;
}
