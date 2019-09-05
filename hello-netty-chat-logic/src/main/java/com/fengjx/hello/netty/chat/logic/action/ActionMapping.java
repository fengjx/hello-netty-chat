package com.fengjx.hello.netty.chat.logic.action;

import com.fengjx.hello.netty.chat.proto.Request;
import com.fengjx.hello.netty.chat.proto.Response;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * @author fengjianxin
 */
@Getter
@Setter
public class ActionMapping {

    private String action;

    private Object bean;

    private Method method;


    public Response invok(Request request) {
        try {
            return (Response) method.invoke(bean, request);
        } catch (Exception e) {
            return Response.newBuilder().build();
        }
    }


}
