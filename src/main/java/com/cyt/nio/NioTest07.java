package com.cyt.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *  Nio 锁
 */
public class NioTest07 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/java/com/cyt/nio/NioTest06.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        FileLock lock = fileChannel.lock(0,7, true);
        System.out.println("valid : " + lock.isValid()); //true
        System.out.println("lock type : " + lock.isShared()); //true,即共享锁
        lock.release();
        randomAccessFile.close();
    }
}
