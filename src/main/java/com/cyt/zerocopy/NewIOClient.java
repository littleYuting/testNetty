package com.cyt.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);
        socketChannel.connect(new InetSocketAddress("localhost", 8899));

        String file_name = "D:\\Tomcat\\apache-tomcat-9.0.21.zip";
        FileChannel fileChannel = new FileInputStream(file_name).getChannel();

        long start_time = System.currentTimeMillis();
        //将数据直接从 fileChannel 读到 socketChannel 中，实现了零拷贝
        long read_num = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送总字节数： " + read_num + "，耗时：" + (System.currentTimeMillis() - start_time));

        socketChannel.close();
    }
}
