package com.backinbusiness.cs_t.client;

import io.netty.channel.*;
import io.netty.handler.stream.ChunkedFile;

import java.io.RandomAccessFile;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        RandomAccessFile raf = new RandomAccessFile("_client_storage/1.pdf", "r");
//        FileInputStream fis = new FileInputStream("_client_storage/2.mp4");
        long file_length = raf.length();
        ChannelFuture sendFileFuture = ctx.writeAndFlush(new ChunkedFile(raf,0,file_length,1024*1024), ctx.newProgressivePromise());
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
