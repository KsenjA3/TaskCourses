package org.example.lesson2;

import org.example.lesson2.distridutionInfo.StudentRegisterHandler;
import org.example.lesson2.model.Register;
import org.example.lesson2.model.Student;
import org.example.lesson2.model.StudentRegister;
import org.example.lesson2.model.Subjects;
import org.example.lesson2.service.FailSubgroupHandler;
import org.example.lesson2.service.GreatSubgroupHandler;
import org.example.lesson2.service.Handler;
import org.example.lesson2.service.PerfectSubgroupHandler;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Применение паттернов Builder, Iterator,
 * Chain of Responsibility, Template Method,
 * Proxy
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
            Map<Subjects, Integer> scoreCard1 = new HashMap<>();
            for (int j = 1; j < 10; j++) {
                int mark=(i%10+j%2);
                scoreCard1.put(Subjects.values()[j], mark==0?10:mark);
            }
//            Map<Subjects,Integer> scoreCard =
//                    IntStream.range(i*13-71,i*99999+31)
//                    .limit(10)
//                     .map(v-> Math.abs(v))
//                    .mapToObj(v->v)
//                    .collect(Collectors.toMap(
//                           k-> Subjects.values()[k%10],
//                           k->{return  k%8+3;},
//                           (first, second) -> second
//                    ));
            proxyStudentRegister.includeStudentToShcool ( "Student_"+i, 78+i%2,
                                            (HashMap<Subjects, Integer>) scoreCard1);
        }
        System.out.println("--------------------------------------------------------");
        System.out.println("* Список студентов School");
        System.out.println("--------------------------------------------------------");
        studentRegister.getStudentRegister().forEach(student -> {
            System.out.println(student.toString());
            System.out.println();
        });

        /**
         * Работа с разными подгруппами студентов
         * с применением паттерна  "Цепочка обязанностей" (Chain of Responsibility)
         */
        System.out.println("--------------------------------------------------------");
        System.out.println("* Применение паттернов Chain of Responsibility, внутри классов которого Iterator \n" +
                "* Применение паттерна Template Method через transferTask.ManageStudent ");
        System.out.println("--------------------------------------------------------");
        Handler handler = new PerfectSubgroupHandler();
        Handler great_PerferctStudent = new GreatSubgroupHandler();
        Handler fail_PerferctStudent = new FailSubgroupHandler();
        handler.setNextHandler(great_PerferctStudent);
        great_PerferctStudent.setNextHandler(fail_PerferctStudent);

        /**
         * Получение списка студентов отчисляемых по неуспеваемости
         */
        System.out.println("* Получение списка студентов отчисляемых по неуспеваемости");
        System.out.println("...........................................................");
        HashSet<Student> failGroup=handler.findSubgroup("fail",studentRegister);


        /**
         * Получение списка студентов зачисляемых на стажировку
         */
        System.out.println("...........................................................");
        System.out.println("* Получение списка студентов зачисляемых на стажировку");
        System.out.println("...........................................................");
        HashSet<Student> greatGroup= handler.findSubgroup("great",studentRegister);

        /**
         * Получение списка студентов принимаемых на работу
         */
        System.out.println("...........................................................");
        System.out.println("* Получение списка студентов принимаемых на работу");
        System.out.println("...........................................................");
        HashSet<Student> perfectGroup=handler.findSubgroup("perfect",studentRegister);

    }


}
