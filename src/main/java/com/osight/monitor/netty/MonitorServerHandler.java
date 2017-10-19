package com.osight.monitor.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author chenw <a href="mailto:chenw@chsi.com.cn">chen wei</a>
 * @version $Id$
 */
public class MonitorServerHandler extends SimpleChannelInboundHandler<String> {
    public MonitorServerHandler() {
        super(true);
    }


    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String[] array = msg.split("@");
        String type = array[0];
        String client = array[1];
        Channel ch = ClientChannelMap.get(client);
        if (ch == null) {
            ch = ctx.channel();
            ClientChannelMap.add(client, ch);
        }
        if ("0".equals(type)) {
            ch.writeAndFlush("2@" + client);
        } else if ("1".equals(type)) {
            System.out.println("收到客户端发来的消息：" + msg);
            String json = array[2];
            ch.writeAndFlush("data is received");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ClientChannelMap.remove(ctx.channel());
    }
}
