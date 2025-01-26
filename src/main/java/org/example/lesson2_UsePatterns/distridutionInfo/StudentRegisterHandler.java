package org.example.lesson2_UsePatterns.distridutionInfo;

import lombok.AllArgsConstructor;
import org.example.lesson2_UsePatterns.model.StudentRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@AllArgsConstructor
public class StudentRegisterHandler implements InvocationHandler {
        private StudentRegister originStudentRegister;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("Проверка данных студента.");
        //code checking security and actuality
        System.out.println("Данные успешно прошли проверку.");
        boolean isSecure = true;

        if (isSecure) method.invoke(originStudentRegister,args);
        else System.out.println("Отказ о зачислении.");

        System.out.println();
        return null;
    }
}
