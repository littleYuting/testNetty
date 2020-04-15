package com.cyt.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class NioTest11 {
    public static <CharsetBuffer> void main(String[] args) throws IOException {
        RandomAccessFile inputFile = new RandomAccessFile("src/main/java/com/cyt/nio/nioTest11_in.txt", "r");
        RandomAccessFile outputFile = new RandomAccessFile("src/main/java/com/cyt/nio/nioTest11_out.txt", "rw");
        FileChannel inChannel = inputFile.getChannel();
        FileChannel outChannel = outputFile.getChannel();

        Long file_len = new File("src/main/java/com/cyt/nio/nioTest11_in.txt").length();
        MappedByteBuffer inputData = inChannel.map(FileChannel.MapMode.READ_ONLY, 0 , file_len);

        System.out.println("============打印出所有的 charset============");
        Charset.availableCharsets().forEach((k, v)->{
            System.out.println(k + " : " + v);
        });
        System.out.println("===========================================");
        //定义一个 utf-8 编解码器，其中 decode 是解码成 char， encode 是编码成 byte
        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        //对读取的数据进行编解码操作
        CharBuffer charBuffer = decoder.decode(inputData);
        ByteBuffer outputData = encoder.encode(charBuffer);

        outChannel.write(outputData);

        inputFile.close();
        outputFile.close();
    }
}
