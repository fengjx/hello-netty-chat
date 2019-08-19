package com.fengjx.hello.netty.chat.auth.service;

import com.fengjx.hello.netty.chat.auth.api.AuthService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author fengjianxin
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public String hello() {
        return "hello: " + appName;
    }
}
