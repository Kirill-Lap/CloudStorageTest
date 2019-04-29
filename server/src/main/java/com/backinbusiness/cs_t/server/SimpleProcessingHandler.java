package com.backinbusiness.cs_t.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SimpleProcessingHandler extends SimpleChannelInboundHandler<Object> {
    private File file;
    private final String PATH = "_server_storage/";

    protected void channelRead0(ChannelHandlerContext ctx, Object in) throws Exception {

        ByteBuf content = (ByteBuf) in;
        Path path = Paths.get(PATH + "1.pdf");
        if (!Files.exists(path)){
            Files.createFile(path);
        }

        /**Почему, независимо от размера ChunkSize, считывается всё равно по 64 кб
         * это из-за того, что я кастую ByteBuf???
         * есть вариант сделать иначе?*/
        int len = content.readableBytes();
        System.out.println("ByteBuf Size: " + len);
        byte[] bytes = new byte[content.readableBytes()];
        content.readBytes(bytes);
        Files.write(path, bytes, StandardOpenOption.APPEND);

    }

}
