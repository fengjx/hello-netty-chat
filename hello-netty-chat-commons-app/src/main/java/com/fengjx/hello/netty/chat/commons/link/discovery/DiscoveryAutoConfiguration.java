package com.fengjx.hello.netty.chat.commons.link.discovery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengjianxin
 */
@Configuration
@ConditionalOnProperty(
        prefix = "hnc.link.discovery",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = false
)
@EnableConfigurationProperties(DiscoveryProperties.class)
public class DiscoveryAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public DiscoveryService discoveryService(DiscoveryProperties properties) throws Exception {
        return new DiscoveryService(properties);
    }

}
