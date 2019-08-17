package com.fengjx.hello.netty.chat.commons.link.discovery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author fengjianxin
 */
@Getter
@Setter
@ToString
public class LinkServerInstance {

    private String host;
    private int port;
    private String description;

    public String getId() {
        return host + ":" + port;
    }

}
