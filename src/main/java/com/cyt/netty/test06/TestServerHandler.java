package com.cyt.netty.test06;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DataType type = msg.getDataType();
        if (type == MyDataInfo.MyMessage.DataType.personType) {
            System.out.println(msg.getPerson().getName());
            System.out.println(msg.getPerson().getAge());
            System.out.println(msg.getPerson().getAddress());
        } else if (type == MyDataInfo.MyMessage.DataType.dogType) {
            System.out.println(msg.getDog().getDogName());
            System.out.println(msg.getDog().getType());
        } else {
            System.out.println(msg.getCat().getCatName());
            System.out.println(msg.getCat().getSize());
        }

    }
}
