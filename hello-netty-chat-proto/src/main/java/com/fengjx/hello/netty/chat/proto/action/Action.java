package com.fengjx.hello.netty.chat.proto.action;

import com.fengjx.hello.netty.chat.proto.Request;
import com.fengjx.hello.netty.chat.proto.Response;
import io.grpc.stub.StreamObserver;

/**
 * @author fengjianxin
 */
public interface Action {

    void invok(Request request, StreamObserver<Response> responseObserver);

}
