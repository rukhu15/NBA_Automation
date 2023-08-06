package com.temp;

import java.lang.reflect.Method;

/**
 * @author Prateek Sethi
 */
public class Home {

    public static void main(String[] args) {
        Test t = new Test();
        Class clazz = t.getClass();
        System.out.println(clazz.getSimpleName());
        Method[] arrayOfMethods=clazz.getDeclaredMethods();
        System.out.println(arrayOfMethods.length);

        for(Method m:arrayOfMethods){
            System.out.println(m.getName());
        }

    }
}
