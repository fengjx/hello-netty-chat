package com.fengjx.hello.netty.cha.link;

import com.fengjx.hello.netty.cha.link.server.LinkServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author fengjianxin
 */
@SpringBootApplication
public class LinkApp {

    @Resource
    private LinkServer linkServer;

    public static void main(String[] args) {
        SpringApplication.run(LinkApp.class, args);
    }



}
