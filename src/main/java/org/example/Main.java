package org.example;

public class Main {
    public static void main(String[] args) {


        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.setFirst(11);
        list.setFirst(22);
        list.setFirst(33);
        list.setFirst(44);

        System.out.println(list);
    }
}