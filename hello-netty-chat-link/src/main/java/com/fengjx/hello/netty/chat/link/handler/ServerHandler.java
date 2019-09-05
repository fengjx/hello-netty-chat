package com.fengjx.hello.netty.chat.link.handler;

import com.fengjx.hello.netty.chat.link.dispatch.AbstractDispatch;
import com.fengjx.hello.netty.chat.link.dispatch.DispatchFactory;
import com.fengjx.hello.netty.chat.link.manager.ChannelManager;
import com.fengjx.hello.netty.chat.proto.Request;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fengjianxin
 */
@Slf4j
@ChannelHandler.Sharable
@Service
public class ServerHandler extends SimpleChannelInboundHandler<Request> {

    @Resource
    private ChannelManager channelManager;
    @Resource
    private DispatchFactory dispatchFactory;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channelManager.addGroup(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        log.info("Receive from: [{}]", ctx.channel().remoteAddress());
        log.info("Server received:\n {}", request);
        AbstractDispatch dispatch = dispatchFactory.getDispatchByType(request.getHeader().getType());
        if (dispatch != null) {
            dispatch.action(ctx, request);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("ctx flush");
        ctx.flush();
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerAdded");
        super.handlerAdded(ctx);
    }
}
