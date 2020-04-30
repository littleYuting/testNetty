package com.cyt.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

public class TestByteBuf2 {
    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        ByteBuf heapByteBuf = Unpooled.buffer();
        ByteBuf directByteBuf = Unpooled.directBuffer();
        compositeByteBuf.addComponents(heapByteBuf, directByteBuf);
        /** 打印结果
         * UnpooledSlicedByteBuf(ridx: 0, widx: 0, cap: 0/0, unwrapped: UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 0, cap: 256))
         * UnpooledSlicedByteBuf(ridx: 0, widx: 0, cap: 0/0, unwrapped: UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(ridx: 0, widx: 0, cap: 256))
         */
        compositeByteBuf.forEach(System.out::println);
        compositeByteBuf.removeComponent(0);
        Iterator<ByteBuf> iterator = compositeByteBuf.iterator();
        /**打印结果：
         * UnpooledSlicedByteBuf(ridx: 0, widx: 0, cap: 0/0, unwrapped: UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(ridx: 0, widx: 0, cap: 256))
         */
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
