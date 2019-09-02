package com.fengjx.hello.netty.chat.link.manager;

import com.fengjx.hello.netty.chat.proto.Response;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fengjianxin
 */
@Slf4j
public class UserClient {

    private Long userId;

    private ChannelGroup group;

    public UserClient(Long userId, Channel channel) {
        this.userId = userId;
        group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        group.add(channel);
    }

    public Long getUserId() {
        return userId;
    }

    public void addChannel(Channel channel) {
        if (group.contains(channel)) {
            log.info("channel contains: {}, {}", userId, channel.id());
            return;
        }
        group.add(channel);
    }

    public ChannelGroup getChannelGroup() {
        return group;
    }

    public void writeAndFlush(Response response) {
        group.writeAndFlush(response);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof UserClient) {
            UserClient client = (UserClient) obj;
            return this.userId.equals(client.userId);
        }
        return false;
    }

}
