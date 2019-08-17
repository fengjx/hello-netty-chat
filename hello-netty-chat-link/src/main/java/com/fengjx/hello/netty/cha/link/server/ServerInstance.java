package com.fengjx.hello.netty.cha.link.server;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author fengjianxin
 */
@Getter
@Setter
@Builder
public class ServerInstance {

    private String name;
    private String ip;
    private String port;
    private String description;



}
