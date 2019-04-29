package com.backinbusiness.cs_t.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.handler.stream.ChunkedFile;

import java.io.RandomAccessFile;

public class ClientHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        String fileName = (String) msg;
        String path = "_client_storage/" + fileName;
        RandomAccessFile raf = new RandomAccessFile(path, "r");

        //Формируем БайтБуф по формату протокола [id][fileNameLength][fileName][file_length]
        long file_length = raf.length();
        byte[] fileNameBytes = fileName.getBytes();
        ByteBufAllocator al = new PooledByteBufAllocator();
        int bufSize=1+4+fileNameBytes.length+8;
        ByteBuf buf = al.buffer(bufSize);
        buf.writeByte(15);
        buf.writeInt(fileNameBytes.length);
        buf.writeBytes(fileNameBytes);
        buf.writeLong(file_length);
        ctx.writeAndFlush(buf);



        ChannelFuture sendFileFuture = ctx.writeAndFlush(new ChunkedFile(raf,0,file_length,1024*1024), ctx.newProgressivePromise());

        //прогрессбар
        ChannelFuture lastContentFuture = sendFileFuture;

        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if (total <0 ) {
                    // total unknown
                    System.err.println(future.channel() + " Transfer progress: " + progress);

                } else {
                    System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
                }
            }

            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.err.println(future.channel() + " Transfer complete");
            }
        });

        lastContentFuture.addListener(ChannelFutureListener.CLOSE);
    }
}
