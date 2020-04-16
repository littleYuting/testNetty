package com.cyt.netty.test01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
    public static void main(String[] args) throws InterruptedException {
        //EventLoopGroup 是个死循环，网络编程的死循环的避不开的
        EventLoopGroup bossGroup = new NioEventLoopGroup();//只负责接收连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();//负责对已接受的连接进行数据处理
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();//服务端一个辅助的启动类
            //方法链的编程风格
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            //优雅关闭，如果连接还未关闭，会先进行一定的处理
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        /** output
         * channel handlerAdded....
         * channel registered....
         * channel active....
         * 请求方法名： GET
         * 执行 channelRead0 ...
         * channel in_active....
         * channel unregistered....
         *
         */
    }
}
