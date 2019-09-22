package com.fengjx.hello.netty.chat.commons.rpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fengjianxin
 */
@Slf4j
@Component
public class RpcServer implements CommandLineRunner, DisposableBean {

    private Server server;

    @Value("${grpc.port}")
    private int port;

    @Resource
    private InvokServiceImpl invokService;

    @Override
    public void run(String... args) throws Exception {
        server = ServerBuilder.forPort(port)
                .addService(invokService)
                .build()
                .start();
        log.info("rpc server started, listening on {}", port);
    }

    @Override
    public void destroy() throws Exception {
        if (server != null) {
            server.shutdown();
        }
        log.info("rpc server stoped");
    }


}
