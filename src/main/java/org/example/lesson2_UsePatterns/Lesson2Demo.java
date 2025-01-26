package org.example.lesson2_UsePatterns;

import org.example.lesson2_UsePatterns.distridutionInfo.StudentRegisterHandler;
import org.example.lesson2_UsePatterns.model.Register;
import org.example.lesson2_UsePatterns.model.Student;
import org.example.lesson2_UsePatterns.model.StudentRegister;
import org.example.lesson2_UsePatterns.model.Subjects;
import org.example.lesson2_UsePatterns.service.*;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Применение паттернов Builder и Proxy,
 * Iterator и Template Method
 * и одного из Chain of Responsibility или Decorator *
 */

public class Lesson2Demo {
    public static void main(String[] args) {

        System.out.println("--------------------------------------------------------");
        System.out.println("* Прием студентов в School");
        System.out.println("* Создание Student c применением паттерна Builder \n" +
                            "* Создание StudentRegister c применением паттерна Proxy");
        System.out.println("--------------------------------------------------------");
        StudentRegister studentRegister = new StudentRegister();
        Register proxyStudentRegister= (Register) Proxy.newProxyInstance(
                StudentRegister.class.getClassLoader(),
//                studentRegister.getClass().getClassLoader(),
                new Class [] {Register.class},
//                studentRegister.getClass().getInterfaces(),
                new StudentRegisterHandler(studentRegister));

        for (int i = 1; i < 20; i++) {
            HashMap<Subjects, Integer> scoreCard1 = new HashMap<>();
            for (int j = 1; j < 10; j++) {
                int mark=(i%10+j%2);
                scoreCard1.put(Subjects.values()[j], mark==0?10:mark);
            }
//            Map<Subjects,Integer> scoreCard =
//                    IntStream.range(i*13-71,i*99999+31)
//                    .limit(10)
//                    .map(v-> Math.abs(v))
//                    .mapToObj(v->v)
//                    .collect(Collectors.toMap(
//                           k-> Subjects.values()[k%10],
//                           k->{return  k%8+3;},
//                           (first, second) -> second
//                    ));
            proxyStudentRegister.includeStudentToShcool ( "Student_"+i, 78+i%2,scoreCard1);
        }
        System.out.println("--------------------------------------------------------");
        System.out.println("* Список студентов School");
        System.out.println("--------------------------------------------------------");
        studentRegister.getStudentRegister().forEach(student -> {
            System.out.println(student.toString());
            System.out.println();
        });


        /**
         * Работа с разными подгруппами студентов 2 способа работы:
         * 1 - через Chain of Responsibility
         * 2 - через Decorator
         * Также в обоих способах внутри классов используестя паттерн Iterator
         * И паттерн Template Method через transferTask.ManageStudent
         */

        HandlerSubgroup handlerSubgroup = new PerfectSubgroupHandlerSubgroup();
        HandlerSubgroup greatStudent = new GreatSubgroupHandlerSubgroup();
        HandlerSubgroup failStudent = new FailSubgroupHandlerSubgroup();

        /**
         * I СПОСОБ
         * С применением паттернов Chain of Responsibility через HandlerSubgroup и его наследников
         */

//        System.out.println("--------------------------------------------------------");
//        System.out.println("* Применение паттернов Chain of Responsibility и Iterator \n" +
//                "* Применение паттерна Template Method через transferTask.ManageStudent ");
//        System.out.println("--------------------------------------------------------");
//
//        handlerSubgroup.setNextHandler( greatStudent);
//        greatStudent.setNextHandler(failStudent);
//
//        System.out.println("* Получение списка студентов принимаемых на работу");
//        System.out.println("...........................................................");
//        HashSet<Student> perfectGroup= handlerSubgroup.findSubgroup("perfect",studentRegister);
//
//        System.out.println("...........................................................");
//        System.out.println("* Получение списка студентов отчисляемых по неуспеваемости");
//        System.out.println("...........................................................");
//        HashSet<Student> failGroup= handlerSubgroup.findSubgroup("fail",studentRegister);
//
//        System.out.println("...........................................................");
//        System.out.println("* Получение списка студентов зачисляемых на стажировку");
//        System.out.println("...........................................................");
//        HashSet<Student> greatGroup= handlerSubgroup.findSubgroup("great",studentRegister);


        /**
         * II СПОСОБ
         * С применением паттернов  Decorator через HandlerSubgroupDecorator
         */

        System.out.println("--------------------------------------------------------");
        System.out.println("* Применение паттернов Decorator через HandlerSubgroupDecorator и Iterator\n" +
                "* Применение паттерна Template Method через transferTask.ManageStudent ");
        System.out.println("--------------------------------------------------------");

        Handler perfectPremiumStudents =new PremiumForSubgroupStudents(handlerSubgroup);
        Handler greatStudents_withNew =new AddStudentToSubgroup(greatStudent);

        System.out.println("* Получение списка студентов принимаемых на работу\n" +
                            "* с дополнительной опцией выплаты премии");
        System.out.println("...........................................................");
        HashSet<Student> perfectGroup= perfectPremiumStudents.findSubgroup("perfect",studentRegister);

        System.out.println("...........................................................");
        System.out.println("* Получение списка студентов зачисляемых на стажировку\n" +
                            "* с дополнительной опцией добавления студента");
        System.out.println("...........................................................");
        HashSet<Student> greatGroup= greatStudents_withNew.findSubgroup("great",studentRegister);
        System.out.println("Новый список стажеров:");
        greatGroup.forEach(System.out::println);

        System.out.println("...........................................................");
        System.out.println("* Получение списка студентов отчисляемых по неуспеваемости");
        System.out.println("...........................................................");
        HashSet<Student> failGroup= failStudent.findSubgroup("fail",studentRegister);

    }


}
