package com.cyt.protobuf;

public class ProtobufTest {
    public static void main(String[] args) throws Exception{
        DataInfo.Student student = DataInfo.Student.newBuilder().
                setName("陈玉婷").setAge(25).setAddress("天津财经大学").build();
        byte[] student2byteArray = student.toByteArray();
        DataInfo.Student student1 = DataInfo.Student.parseFrom(student2byteArray);
        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());
    }
}
