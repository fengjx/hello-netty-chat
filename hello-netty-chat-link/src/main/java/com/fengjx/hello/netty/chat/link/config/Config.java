package com.fengjx.hello.netty.chat.link.config;

import com.fengjx.hello.netty.chat.commons.utils.OkHttpUtils;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengjianxin
 */
@Configuration
public class Config {

    @Bean
    public OkHttpClient okHttpClient() {
        return OkHttpUtils.build(100, 3, 5);
    }

}
