package com.fengjx.hello.netty.chat.link.dispatch;

import com.fengjx.hello.netty.chat.link.manager.ChannelManager;
import com.fengjx.hello.netty.chat.link.proto.InvokServiceClient;
import com.fengjx.hello.netty.chat.proto.Request;
import com.fengjx.hello.netty.chat.proto.RequestType;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author fengjianxin
 */
@Component
public class ExitDispatch extends AbstractDispatch {

    @Resource
    private ChannelManager channelManager;

    @Override
    public RequestType type() {
        return null;
    }

    @Override
    public void action(ChannelHandlerContext ctx, Request request) throws Exception {
        channelManager.removeChannel(ctx.channel());
    }
}
