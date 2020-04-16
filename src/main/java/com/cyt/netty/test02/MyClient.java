package com.cyt.netty.test02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {

    public static void main(String[] args) throws InterruptedException {
        //相比 server 少一个 eventGroup
        EventLoopGroup eventGroup = new NioEventLoopGroup();
        try {
            //具备连接所必备的信息，作用如同 serverBootstrap
            Bootstrap bootstrap = new Bootstrap();
            /**
             * 1)注意这里是 NioSocketChannel，而 server 是 NioServerSocketChannel;
             * 2)server 端有 handler（bossGroup） 和 childHandler（workerGroup），而 client 端只有 handler
             */
            bootstrap.group(eventGroup).channel(NioSocketChannel.class).handler(new MyClientInitializer());
            //server 端是 bind，而 client 端是 connect；
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            eventGroup.shutdownGracefully();
        }
    }
}
