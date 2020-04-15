package com.cyt.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * buffer 和 OutputStream 两个玩“读写”
 */
public class NioTest03 {
    public static void main(String[] args) throws IOException {

        FileOutputStream outputStream = new FileOutputStream("src/main/java/com/cyt/nio/NioTest03.txt");
        FileChannel channel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(50);
        byte[] bytes = "hello world, I want to go school".getBytes();

        byteBuffer.put(bytes);

        byteBuffer.flip();

        channel.write(byteBuffer);
        outputStream.close();

    }
}
