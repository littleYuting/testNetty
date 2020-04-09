package com.cyt.netty.test06;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int flag = new Random().nextInt(3);
        MyDataInfo.MyMessage message = null;

        if (flag == 0) {
            message = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.personType)
                    .setPerson(MyDataInfo.Person.newBuilder().setName("cyt").setAge(25).setAddress("tjufe").build())
                    .build();
        } else if (flag == 1) {
            message = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.dogType)
                    .setDog(MyDataInfo.Dog.newBuilder().setDogName("一只狗").setType("中华田园犬").build())
                    .build();
        } else {
            message = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.catType)
                    .setCat(MyDataInfo.Cat.newBuilder().setCatName("一只喵").setSize("胖").build())
                    .build();
        }
        ctx.channel().writeAndFlush(message);
    }


}
