package com.cyt.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class TestByteBuf0 {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buf.writeByte(i);
        }
        //通过索引来访问 byte，并不会改变 buf 的读索引和写索引
//        for (int i = 0; i < buf.capacity(); i++) {
//            System.out.println(buf.getByte(i));
//        }
        for (int i = 0; i < buf.capacity(); i++) {
            System.out.println(buf.readByte());//在调用时会隐式的进行 readIndex++ 操作；
        }
    }
}
