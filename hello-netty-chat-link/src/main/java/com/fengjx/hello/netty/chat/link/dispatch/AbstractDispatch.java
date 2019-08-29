package com.fengjx.hello.netty.chat.link.dispatch;

import com.fengjx.hello.netty.chat.link.protobuf.RequestProtos;
import com.fengjx.hello.netty.chat.link.protobuf.ResponseProtos;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author fengjianxin
 */
public abstract class AbstractDispatch {

    public abstract RequestProtos.ActionType actionType();

    public abstract ResponseProtos.Response action(ChannelHandlerContext ctx, RequestProtos.Request request) throws Exception;

}
