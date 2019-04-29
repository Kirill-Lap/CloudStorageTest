package com.backinbusiness.cs_t.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyClient {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 8189;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new ChunkedWriteHandler(),
                                    new ClientHandler());
                        }
            });

            ChannelFuture f = bootstrap.connect(host, port).sync();
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
