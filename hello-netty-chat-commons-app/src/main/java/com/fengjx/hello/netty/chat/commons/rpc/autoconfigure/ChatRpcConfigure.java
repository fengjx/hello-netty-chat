package com.fengjx.hello.netty.chat.commons.rpc.autoconfigure;

import com.fengjx.grpc.server.server.GrpcServer;
import com.fengjx.grpc.server.springboot.autoconfigure.GrpcServerAutoConfigure;
import com.fengjx.hello.netty.chat.commons.rpc.InvokServiceImpl;
import com.fengjx.hello.netty.chat.proto.InvokServiceGrpc;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengjianxin
 */
@Configuration
@AutoConfigureBefore(GrpcServerAutoConfigure.class)
@ConditionalOnClass(GrpcServer.class)
public class ChatRpcConfigure {

    @Bean
    @ConditionalOnMissingBean
    public InvokServiceGrpc.InvokServiceImplBase invokServiceI() {
        return new InvokServiceImpl();
    }


}
