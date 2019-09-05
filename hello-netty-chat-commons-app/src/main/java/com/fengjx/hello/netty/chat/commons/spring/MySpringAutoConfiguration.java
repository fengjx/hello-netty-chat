package com.fengjx.hello.netty.chat.commons.spring;

import com.fengjx.hello.netty.chat.commons.utils.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengjianxin
 */
@Configuration
public class MySpringAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpringUtils springContextUtil() {
        return new SpringUtils();
    }


}