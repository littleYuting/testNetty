package com.cyt.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
    public static void main(String[] args)  throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.getReuseAddress();
        serverSocket.bind(new InetSocketAddress(8899));

        while (true) {
             SocketChannel socketChannel = serverSocketChannel.accept();
            //此处没有涉及 selector，所以可设置为阻塞态
             socketChannel.configureBlocking(true);
             ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
             int read = 0;
             while (-1 != read) {
                 try {
                     read = socketChannel.read(byteBuffer);
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
                 byteBuffer.rewind();//可以反复往 byteBuffer 中写入，此处有一个问题：rewind 和 clear 在此处有功能区别吗？
             }
        }
    }
}
