package com.fengjx.hello.netty.chat.link.config;

import com.fengjx.hello.netty.chat.link.proto.InvokServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengjianxin
 */
@Configuration
public class GrpcConfig {

    @Bean
    public InvokServiceClient invokServiceClient() {
        return new InvokServiceClient("localhost", 5001);
    }

}
