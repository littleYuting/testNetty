package com.cyt.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  MappedByteBuffer 直接进行堆外内存的读写
 */
public class NioTest06 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/java/com/cyt/nio/NioTest06.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'a');
        mappedByteBuffer.put(3, (byte) 'b');

        while (mappedByteBuffer.hasRemaining()) {
            System.out.println((char)mappedByteBuffer.get());
        }

        randomAccessFile.close();
        /**
         * NioTest06.txt 中的内容：cytfighting
         * 结果： aytbi，将 0，3 位置上的内容作了替换，且只截取了 5 位
         */
    }
}
