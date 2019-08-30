package com.fengjx.hello.netty.chat.link.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author fengjianxin
 */
@Component
public class ServerInitializer extends ChannelInitializer<Channel> {

    @Resource
    private ServerHandler serverHandler;

    @Override
    protected void initChannel(Channel ch) {
        ch.pipeline()
                //3 秒没有向客户端发送消息就发生心跳
                .addLast(new IdleStateHandler(3, 0, 0))
                // google Protobuf 编解码
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(serverHandler);
    }
}