package com.cyt.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 关于 Buffer 的 Scattering 和 Gathering
 * 1) 有多个 byteBuffer 时，会先先按着顺序把 buffer 填满再去往下一个 buffer 中塞东西；
 */
public class NioTest08 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int message_len = 2 + 3 + 4;
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        SocketChannel channel = serverSocketChannel.accept();

        while (true) {
            Arrays.asList(byteBuffers).stream()
                    .forEach(byteBuffer -> byteBuffer.clear());
            int bytesRead = 0;
            while (bytesRead < message_len) {
                Long r = channel.read(byteBuffers);
                bytesRead += r;
                System.out.println("bytesRead : " + bytesRead);
                Arrays.asList(byteBuffers).stream()
                        .map(byteBuffer -> "position : " + byteBuffer.position() + ", limit : " + byteBuffer.limit())
                        .forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).stream()
                    .forEach(byteBuffer -> byteBuffer.flip());

            int bytesWrite = 0;
            while (bytesWrite < message_len) {
                Long r = channel.write(byteBuffers);
                bytesWrite += r;
            }
            System.out.println("bytesRead : " + bytesRead + " , bytesWrite : " + bytesWrite + " , message_len : " + message_len);
        }
    }
}
