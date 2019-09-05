package com.fengjx.hello.netty.chat.link.dispatch;

import com.fengjx.hello.netty.chat.link.proto.InvokServiceClient;
import com.fengjx.hello.netty.chat.proto.Request;
import com.fengjx.hello.netty.chat.proto.RequestType;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author fengjianxin
 */
@Component
public class AuthDispatch extends AbstractDispatch {

    @Resource
    private InvokServiceClient invokServiceClient;

    @Override
    public RequestType type() {
        return null;
    }

    @Override
    public void action(ChannelHandlerContext ctx, Request request) throws Exception {
        invokServiceClient.invok(request, ctx::writeAndFlush);
    }
}
