package com.fengjx.hello.netty.chat.link.manager;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengjianxin
 */
@Slf4j
@Component
public class ChannelManager {

    private static final ChannelGroup GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Map<Integer, ChannelGroup> ROOM_GROUP = Maps.newConcurrentMap();
    private static final Map<Long, UserClient> USER_MAP = Maps.newConcurrentMap();
    private static final Map<ChannelId, Long> USER_MAP_REVERSE = Maps.newConcurrentMap();

    public Long getUserIdByChannelId(ChannelId channelId) {
        return USER_MAP_REVERSE.get(channelId);
    }

    public void addGroup(Channel channel) {
        GROUP.add(channel);
        log.info("Client [{}] connected", channel.remoteAddress());
    }



}
