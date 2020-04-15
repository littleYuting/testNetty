package com.cyt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio 的 selector
 */
public class NioTest09 {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];
        int port = 5000;
        for (int i = 0; i < ports.length; i++) {
            ports[i] = port;
            port++;
        }

        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口 ： " + ports[i]);
        }

        while (true) {
            int nums = selector.select();//阻塞，一直等到关注事件的发生，并返回发生事件的个数
            System.out.println("nums : " + nums);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys : " + selectionKeys);

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("获得客户端连接: " + socketChannel);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int bytesRead = 0;
                    ByteBuffer byteBuffer = ByteBuffer.allocate(15);
                    while (true) {
                        byteBuffer.clear();
                        int r = socketChannel.read(byteBuffer);
                        if (r <= 0) {
                            break;
                        }

                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        bytesRead += r;
                    }
                    System.out.println("读取：" + bytesRead + "，来自于:" + socketChannel);
                }
                iterator.remove();
            }
        }
    }
}
