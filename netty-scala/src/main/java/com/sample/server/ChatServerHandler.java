package com.sample.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by JEG032 on 04/11/2016.
 */
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("CHANNEL REGISTERED: " + ctx.name());

        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush("SERVER [" + incoming.remoteAddress() + "] has joined \n");
            }
        }

        channels.add(incoming);
        incoming.writeAndFlush("WELCOME !! \n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("CHANNEL UNREGISTERED: " + ctx.name());

        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush("SERVER [" + incoming.remoteAddress() + "] has left \n");
            }
        }

        channels.remove(incoming);
        incoming.writeAndFlush("BYE !!\n");
    }

//
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        Channel incoming = ctx.channel();

        System.out.println("MESSAGE RECEIVED FROM " + incoming.remoteAddress() + ": " + message);
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]: " + message + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
