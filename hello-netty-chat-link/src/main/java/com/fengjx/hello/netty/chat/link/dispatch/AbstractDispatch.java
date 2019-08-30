package com.fengjx.hello.netty.chat.link.dispatch;

import com.fengjx.hello.netty.chat.proto.Request;
import com.fengjx.hello.netty.chat.proto.RequestType;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author fengjianxin
 */
public abstract class AbstractDispatch {

    public abstract RequestType type();

    public abstract void action(ChannelHandlerContext ctx, Request request) throws Exception;

}
