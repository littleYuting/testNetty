package com.cyt.netty.test01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;


public class  TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 问方式有两种：1）浏览器访问 2）curl "http:\\localhost:8899"
     * 1）浏览器访问结果因浏览器而异，有的浏览器还会以访问网站图标的形式再访问一次，造成 channelRead0 执行两次；
     * 2）服务端对于浏览器发送的 http 命令有个 keepalive 属性，所以在执行 channelRead0之后可能需要等待一段时间才会执行inactive 和 unregistered
     * 3）curl由于是一个完整的网络传输命令，所以在执行之后会立即调用 inactive 和 unregistered
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(msg.getClass());

        System.out.println(ctx.channel().remoteAddress());
        Thread.sleep(6000);
        if (msg instanceof  HttpRequest){
            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println("请求方法名： " + httpRequest.method().name());
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求 favicon.ico");
                return;
            }
            System.out.println("执行 channelRead0 ...");
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "test/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active....");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered....");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel handlerAdded....");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel in_active....");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered....");
        super.channelUnregistered(ctx);
    }
}
