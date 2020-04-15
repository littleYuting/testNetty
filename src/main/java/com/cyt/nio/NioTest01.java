package com.cyt.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * 只有 buffer 自己玩“读写”
 */
public class NioTest01 {
    /**
     * 1) 每次 put 都在当前 position 的基础上向后追加，且 put 进多少都会在原来基础上使 position 后移多少；
     * 2） flip 操作 会先将 limit 重新赋值为旧的 position 值，而旧的 posiotion 值重置为 0；
     * 3） 如果对于一个 buffer 存在多次 flip，则每次 flip 都会清除前一次 flip 能够读取的数据，最终能够读取的内容以两次 flip
     * 之间 put 的数据为准；
     * 4） 读取的范围为 0 ~ limit，buffer.hasRemaining() 能够打印的范围为  position ~ limit，对于 0 ~ position 只能通过
     * buffer.get() 获得
     * 5） clear() 将 buffer 恢复成一个初始化状态，即 position = 0， limit = capacity;
     * 6) 如果想在某次 flip 后追加数据，可使用 buffer.rewind() ，该方法会使 position 重新变成 0 ，limit 保持不变；
     */
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(25);
        for (int i = 0; i < buffer.capacity()/2; i++) {
            int random_v = new SecureRandom().nextInt();
            buffer.put(random_v);
        }
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        buffer.flip();
        buffer.put(new int[]{1, 2, 3, 4});
        buffer.put(5);
        System.out.println("----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("----------------");
        buffer.rewind();
//        buffer.flip();
//        System.out.println(buffer.position());
//        System.out.println(buffer.limit());
//        System.out.println(buffer.capacity());
//        System.out.println("----------------");
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        System.out.println("---------------");
        System.out.println(buffer.get(3));
    }
}
