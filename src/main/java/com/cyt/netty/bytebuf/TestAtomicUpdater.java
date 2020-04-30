package com.cyt.netty.bytebuf;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class TestAtomicUpdater {
    public static void main(String[] args) {
        Person person = new Person();

//        for (int i = 0; i < 10; ++i) {
//            Thread thread = new Thread(()->{
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(person.age++);
//            });
//            thread.start();
//        }

        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread(()->{
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
                System.out.println(updater.incrementAndGet(person));
            });
            thread.start();
        }
    }
}

class Person {
    volatile int age;
}
