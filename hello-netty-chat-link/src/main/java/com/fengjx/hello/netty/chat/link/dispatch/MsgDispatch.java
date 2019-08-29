package com.fengjx.hello.netty.chat.link.dispatch;

import com.fengjx.hello.netty.chat.link.protobuf.RequestProtos;
import com.fengjx.hello.netty.chat.link.protobuf.ResponseProtos;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author fengjianxin
 */
public class MsgDispatch extends AbstractDispatch {


    @Override
    public RequestProtos.ActionType actionType() {
        return null;
    }

    @Override
    public ResponseProtos.Response action(ChannelHandlerContext ctx, RequestProtos.Request request) throws Exception {



        return null;
    }
}
