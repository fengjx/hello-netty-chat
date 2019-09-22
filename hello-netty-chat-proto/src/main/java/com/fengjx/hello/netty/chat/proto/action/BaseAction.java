package com.fengjx.hello.netty.chat.proto.action;

import com.fengjx.hello.netty.chat.proto.Request;
import com.fengjx.hello.netty.chat.proto.Response;
import io.grpc.stub.StreamObserver;

/**
 * @author fengjianxin
 */
public abstract class BaseAction implements Action {

    @Override
    public void invok(Request request, StreamObserver<Response> responseObserver) {
        Response response = doAcrion(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    abstract Response doAcrion(Request request);


}
