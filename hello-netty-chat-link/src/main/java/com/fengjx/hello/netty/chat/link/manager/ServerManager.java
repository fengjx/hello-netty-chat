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
public class ServerManager {

    private static final ChannelGroup GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Map<String, ChannelId> USER_MAP = Maps.newConcurrentMap();
    private static final Map<ChannelId, String> USER_MAP_REVERSE = Maps.newConcurrentMap();

    public String getUserIdByChannelId(ChannelId channelId) {
        return USER_MAP_REVERSE.get(channelId);
    }

    public void addGroup(Channel channel) {
        GROUP.add(channel);
        log.info("Client [{}] connected", channel.remoteAddress());
    }

}
