package com.cyt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * nio 的 selector
 */
public class NioTestServer10 {

    public static Map<String, SocketChannel> client_map = new HashMap<>();

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocket.bind(address);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                final SocketChannel[] client = new SocketChannel[1];

                selectionKeys.forEach(selectionKey -> {
                    try {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client[0] = server.accept();
                            client[0].configureBlocking(false);
                            client[0].register(selector, SelectionKey.OP_READ);

                            String key = "【" + UUID.randomUUID().toString() + "】";
                            client_map.put(key, client[0]);

                        } else if (selectionKey.isReadable()) {
                            client[0] = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int bytesRead = client[0].read(readBuffer);

                            if (bytesRead > 0) {
                                readBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String receive_msg = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(client[0] + " : " + receive_msg);
                                String send_key = null;
                                for (Map.Entry<String, SocketChannel> entry : client_map.entrySet()) {
                                    if (client[0] == entry.getValue()) {
                                        send_key = entry.getKey();
                                        break;
                                    }
                                }
                                for (Map.Entry<String, SocketChannel> entry : client_map.entrySet()) {
                                    SocketChannel channel = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((send_key + ":" + receive_msg).getBytes());
                                    writeBuffer.flip();
                                    channel.write(writeBuffer);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        selectionKeys.clear();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
