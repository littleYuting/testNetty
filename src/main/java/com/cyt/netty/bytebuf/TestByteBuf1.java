package com.cyt.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class TestByteBuf1 {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.copiedBuffer("陈hello world", Charset.forName("utf-8"));
        if (buf.hasArray()) {
            byte[] bytes = buf.array();
            System.out.println(new String(bytes, Charset.forName("utf-8")));
            System.out.println(buf);//UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 11, cap: 33)

            System.out.println(buf.arrayOffset());
            System.out.println(buf.readerIndex());
            System.out.println(buf.writerIndex());
            System.out.println(buf.capacity());

            System.out.println(buf.readableBytes());//可读字节：writerIndex-readerIndex

            for (int i = 0; i < buf.readableBytes(); i++) {
                System.out.println((char) buf.getByte(i));
            }

            System.out.println(buf.getCharSequence(0,4, Charset.forName("utf-8")));
        }
    }
}