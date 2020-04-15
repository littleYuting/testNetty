package com.cyt.nio;

import java.nio.ByteBuffer;

public class NioTest05 {
    public static void main(String[] args) {
//        test01();
//        test02();
        test03();
    }

    /**
     * 一个读写 buffer 能通过 asReadOnlyBuffer() 方法返回一个只读 buffer，但一个只读 buffer 不能转换为读写 buffer；
     * 1)只读 buffer 是对原读写 buffer 的浅拷贝，position、limit 的初始值与原有 buffer 保持一致，byteBuffer.flip() 和 readOnlyBuffer.flip()
     * 都会使 limit=position，position=0；
     * 2）虽然不能往只读 buffer 中 put 数据，但能往原有 buffer 中 put，只读 buffer 会相应跟着改变，也就是说只读 buffer 和原有的
     * 读写 buffer 共享同一数组；
     */
    public static void test03(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);//byteBuffer.getClass = HeapByteBuffer
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();//readOnlyBuffer.getClass = HeapByteBufferR

        readOnlyBuffer.flip();//与 byteBuffer.flip(); 二选一
        System.out.println("原有 buffer 的 position：" + byteBuffer.position());//10
        System.out.println("readOnlybuffer 的 position：" + readOnlyBuffer.position());//0

        byteBuffer.position(0);
        byteBuffer.put((byte) 10);

//        会报错：java.nio.ReadOnlyBufferException，只读 buffer 不能put数据；
//        readOnlyBuffer.position(0);
//        readOnlyBuffer.putChar('c');

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

    }

    /**
     * byteBuffer 不只能 put 字节类型，还能 put 其他基本数据类型，但是必须对应数据类型 get
     */
    public static void test01(){
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.putChar('c');
        buffer.putInt(15);
        buffer.putDouble(12.34);
        buffer.putFloat(1.2f);
        buffer.putLong(178798888L);
        buffer.putShort((short) 12);
        buffer.flip();
        System.out.println(buffer.getChar());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getFloat());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getShort());
    }

    /**
     * slice buffer 和原有的 buffer 共享同一底层数组
     * 1）与 readOnlyBuffer 不同的是，sliceBuffer 与原有 buffer 的 position、limit 独立;
     * 2) 不管是修改 原有buffer 还是 sliceBuffer，都会相应的修改对方 buffer 中的内容；
     */
    public static void test02(){
        //需求：将 buffer 中 index 为 4~6 对应的数变为原来的 2 倍
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i=0; i < buffer.capacity(); i++) {
             buffer.put((byte) i);
        }
        buffer.position(4);
        buffer.limit(7);

        ByteBuffer sliceBuffer = buffer.slice();

        for (int i=0; i < sliceBuffer.capacity(); i++) {
            Byte b = sliceBuffer.get(i);
            sliceBuffer.put((byte) (b*2));
        }
        System.out.println("****************");
        buffer.position(sliceBuffer.position()-1);
        buffer.put((byte) 90);
        buffer.clear();
//        buffer.position(0);
//        buffer.limit(buffer.capacity());

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        System.out.println("-------------");
        buffer.position(4);
        buffer.put((byte) 88);
        sliceBuffer.clear();
        while (sliceBuffer.hasRemaining()) {
            System.out.println(sliceBuffer.get());
        }
    }
}
