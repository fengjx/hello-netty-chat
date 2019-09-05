package com.fengjx.hello.netty.chat.link.manager;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author fengjianxin
 */
@Slf4j
@Component
public class ChannelManager {

    private static final ChannelGroup ALL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Map<Integer, ChannelGroup> ROOM_GROUP = Maps.newConcurrentMap();
    private static final Map<Long, LinkedList<Integer>> USER_ROOM_REVERSE = Maps.newConcurrentMap();
    private static final Map<Long, ChannelGroup> USER_CHANNEL = Maps.newConcurrentMap();
    private static final Map<ChannelId, Long> USER_CHANNEL_REVERSE = Maps.newConcurrentMap();

    public Long getUserIdByChannelId(ChannelId channelId) {
        return USER_CHANNEL_REVERSE.get(channelId);
    }

    public void addUserChannel(Long userId, Channel channel) {
        ChannelGroup userGroup = USER_CHANNEL.get(userId);
        if (userGroup == null) {
            userGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            USER_CHANNEL.putIfAbsent(userId, userGroup);
        }
        userGroup.add(channel);
        USER_CHANNEL_REVERSE.put(channel.id(), userId);
        ALL_GROUP.add(channel);
        log.info("Client [{}] connected", channel.remoteAddress());
    }

    public ChannelGroup getUserChannels(Long userId) {
        return USER_CHANNEL.get(userId);
    }

    /**
     * 通过channel获得userId
     */
    public Long getUserIdByChannel(Channel channel) {
        ChannelId channelId = channel.id();
        return USER_CHANNEL_REVERSE.get(channelId);
    }

    private void removeUserChannel(Channel channel) {
        ChannelId channelId = channel.id();
        Long userId = USER_CHANNEL_REVERSE.get(channelId);
        ChannelGroup channelGroup = USER_CHANNEL.get(userId);
        if (channelGroup != null) {
            channelGroup.remove(channel);
        }
        USER_CHANNEL_REVERSE.remove(channelId);
    }

    public void removeChannel(Channel channel) {
        ALL_GROUP.remove(channel);
        removeUserChannel(channel);
    }

    /**
     * 获得所有Channel
     */
    public ChannelGroup getAllChannels() {
        return ALL_GROUP;
    }

    /**
     * 进入房间（群聊）
     */
    public void addRoomChannel(Integer roomId, Channel channel) {
        ChannelGroup roomGroup = ROOM_GROUP.get(roomId);
        if (roomGroup == null) {
            roomGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            ROOM_GROUP.putIfAbsent(roomId, roomGroup);
        }
        roomGroup.add(channel);
    }

    public void removeRoomChannel(Integer roomId, Channel channel) {
        ChannelGroup roomGroup = ROOM_GROUP.get(roomId);
        if (roomGroup != null) {
            roomGroup.remove(channel);
        }
    }

    /**
     * 获得房间channel
     */
    public ChannelGroup getRoomChannels(Integer roomId) {
        return ROOM_GROUP.get(roomId);
    }


    /**
     * 踢人下线
     */
    public void kickUser(Long userId){

    }

}
