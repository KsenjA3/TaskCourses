package org.example;

import org.example.lesson_1.MyLinkedList;
import org.example.lesson_1.MyList;

public class Main {
    public static void main(String[] args) {
        MyList<Integer> list = new MyLinkedList<>();
        list.setFirst(11);
        list.setFirst(22);
        list.setFirst(33);
        list.setFirst(44);

        System.out.println(list);
    }
}