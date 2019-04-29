package com.backinbusiness.cs_t.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class ProtocolHandler extends ChannelInboundHandlerAdapter {
    private int fileNameLength;
    private int state = -1;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String fileName;
        if (state == -1) {
            byte firstByte = buf.readByte();
            System.out.println(firstByte);
            state = 0;
        }
        if (state == 0) {
            if (buf.readableBytes()<4) {
                return;
            }
            fileNameLength = buf.readInt();
            state = 1;
        }
        if (state == 1) {
            if (buf.readableBytes() < fileNameLength) {
                return;
            }
            byte[] data = new byte[fileNameLength];
            buf.readBytes(data);
            fileName = new String(data);
            System.out.println(fileName);
            SimpleProcessingHandler.setFileName(fileName);
            state = 2;
        }
        if (state == 2) {
            ctx.fireChannelRead(msg);
        }

    }
}
