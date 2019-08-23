package com.fengjx.hello.netty.chat.link.server;

import com.fengjx.hello.netty.chat.link.handler.ServerInitializer;
import com.fengjx.hello.netty.chat.auth.api.AuthService;
import com.fengjx.hello.netty.chat.commons.link.discovery.DiscoveryService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fengjianxin
 */
@Slf4j
@Component
public class LinkServer implements CommandLineRunner, DisposableBean {

    @Value("${hnc.link.discovery.instance.port}")
    private int port;

    @Resource
    private ServerInitializer initializer;

    @Resource
    private DiscoveryService discoveryService;

    @Reference
    private AuthService authService;

    private EventLoopGroup group = new NioEventLoopGroup(5);
    private EventLoopGroup worker = new NioEventLoopGroup(20);


    private void start() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        b.group(group, worker).channel(NioServerSocketChannel.class).childHandler(initializer);
        b.childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture f = b.bind(port).sync();
        if (f.isSuccess()) {
            String hello = authService.hello();
            log.info("auth hello: {}", hello);
            discoveryService.register();
            log.info("link server start success: {}", port);
        }
        f.channel().closeFuture().sync();
    }


    private void stop() throws Exception {
        group.shutdownGracefully().sync();
        worker.shutdownGracefully().sync();
    }


    @Override
    public void run(String... args) throws Exception {
        this.start();
    }


    @Override
    public void destroy() throws Exception {
        this.stop();
    }
}
