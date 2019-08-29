package com.fengjx.hello.netty.chat.link.handler;

import com.fengjx.hello.netty.chat.link.auth.Authentication;
import com.fengjx.hello.netty.chat.link.dispatch.AbstractDispatch;
import com.fengjx.hello.netty.chat.link.dispatch.DispatchFactory;
import com.fengjx.hello.netty.chat.link.manager.ServerManager;
import com.fengjx.hello.netty.chat.link.protobuf.RequestProtos;
import com.fengjx.hello.netty.chat.link.protobuf.ResponseProtos;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fengjianxin
 */
@Slf4j
@ChannelHandler.Sharable
@Service
public class ServerHandler extends SimpleChannelInboundHandler<RequestProtos.Request> {

    @Resource
    private ServerManager serverManager;
    @Resource
    private DispatchFactory dispatchFactory;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        serverManager.addGroup(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestProtos.Request request) throws Exception {
        log.info("Receive from: [{}]", ctx.channel().remoteAddress());
        log.info("Server received:\n {}", request);
        AbstractDispatch dispatch = dispatchFactory.getDispatchByType(request.getHeader().getActionType());
        if (dispatch != null) {
            dispatch.action(ctx, request);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("ctx flush");
        ctx.flush();
    }
}
