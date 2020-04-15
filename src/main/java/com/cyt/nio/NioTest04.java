package com.cyt.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * buffer 和 InputStream、OutputStream 三个玩“读写”
 */
public class NioTest04 {
    public static void main(String[] args) throws IOException {
        testIncludeBuffer();
    }

    public static void testIncludeBuffer() throws IOException{
        //需求：通过 buffer 和 channel 实现将从 NioTest03.txt 中读取的内容写到 NioTest04.txt 中
        ByteBuffer buffer = ByteBuffer.allocate(4);

        FileInputStream fileInputStream = new FileInputStream("src/main/java/com/cyt/nio/NioTest03.txt");
        FileChannel inChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/java/com/cyt/nio/NioTest04.txt");
        FileChannel outChannel = fileOutputStream.getChannel();

        /**
         * 1)如果将 buffer.clear() 注释掉的话，会使得 buffer 固定缓存不超过其容量大小的内容，在第一次读取之后由于
         * position = limit，所以 read = 0，导致会一直向目标文件写 hell；
         * 2）buffer.clear() 的功能就是将上次存入 buffer 的内容清空，允许继续读入新的内容；
         */
        while (true) {
            buffer.clear();
            int read = inChannel.read(buffer);
            System.out.println(read);
            if (-1 == read) {
                break;
            }
            buffer.flip();
            outChannel.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
    public static void testNoBuffer() throws IOException{
        //需求：只使用 Stream 实现将从 NioTest03.txt 中读取的内容写到 NioTest05.txt 中
        FileInputStream in = new FileInputStream("src/main/java/com/cyt/nio/NioTest03.txt");
        FileOutputStream out = new FileOutputStream("src/main/java/com/cyt/nio/NioTest05.txt");

        byte[] bytes = new byte[50];
        //记录已经存取的有效字节个数，结尾读取的为 -1；
        int len = 0;
        while((len = in.read(bytes)) != -1) {
            //从 偏移量 offer 开始写len个字节
            out.write(bytes, 0,len);
            out.write("\r\n".getBytes());
        }
        out.close();
        in.close();

    }
}
