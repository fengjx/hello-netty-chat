package com.fengjx.hello.netty.chat.commons.link.discovery;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fengjianxin
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "hnc.link.discovery")
public class DiscoveryProperties {

    private boolean enabled = false;

    private String zkConnect = "127.0.0.1:2181";
    private String registerName = "hnc-link";
    private String registerPath = "/" + registerName;
    private int baseSleepTimeMs = 3000;
    private int maxRetries = 3;
    private LinkServerInstance instance = new LinkServerInstance();

}
