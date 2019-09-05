package com.fengjx.hello.netty.chat.logic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author fengjianxin
 */
@RestController
public class HelloCpntroller {

    @RequestMapping("/ping")
    public Mono<String> ping() {
        return Mono.just("pong");
    }


}
