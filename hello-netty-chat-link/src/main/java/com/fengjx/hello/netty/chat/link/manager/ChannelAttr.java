package com.fengjx.hello.netty.chat.link.manager;

import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;

/**
 * @author fengjianxin
 */
@Getter
@Setter
public class ChannelAttr {

    public static final AttributeKey<ChannelAttr> NETTY_CHANNEL_KEY = AttributeKey.valueOf("connect.time");


    private long connectTime;

    public ChannelAttr() {
        this.connectTime = System.currentTimeMillis();
    }

}
