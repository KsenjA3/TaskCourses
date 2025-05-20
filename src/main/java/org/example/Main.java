package org.example;

public class Main {
    public static void main(String[] args) {
//        List<String> l1= new ArrayList<>();
//        List<String> l2 = new LinkedList<>();
//        List<Object> lo = new ArrayList<>();
//        l1.add("John");
//        l1.add("FFFFF");

//        Iterator<String> iterator = l1.iterator();
//        ListIterator<String> listIterator= l1.listIterator();
//
//        listIterator.next();
//        l1.add("ttt");
//        listIterator.next();

//        final Student student1 = Student.builder().name("111").build();
//        Student student2 = Student.builder().name("222").build();
//        student1= student2;
//        student2=student1;

        Bicycle bicycle = new Bicycle("Peugeot", 120, 40);
        Bicycle.Seat seat = bicycle.new Seat();

        seat.getSeatParam();



    }
}