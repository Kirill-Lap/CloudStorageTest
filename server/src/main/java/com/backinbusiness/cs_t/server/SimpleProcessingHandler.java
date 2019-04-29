package com.backinbusiness.cs_t.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SimpleProcessingHandler extends SimpleChannelInboundHandler<Object> {
    private static final String PATH = "_server_storage/";
    private static String fullPath;


    public static void setFileName(String fileName) {
        fullPath = PATH + fileName;
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object in) throws Exception {
        ByteBuf buf = (ByteBuf) in;
        Path path = Paths.get(fullPath);
        if (!Files.exists(path)){
            Files.createFile(path);
        }
        System.out.println(fullPath);

        /**На что влияет размер ChunkSize??*/
        int len = buf.readableBytes();
        System.out.println("ByteBuf Size: " + len);
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        Files.write(path, bytes, StandardOpenOption.APPEND);
    }

}
