package com.fengjx.hello.netty.chat.commons.rpc;

import com.fengjx.grpc.server.springboot.annotation.GrpcService;
import com.fengjx.hello.netty.chat.commons.utils.SpringUtils;
import com.fengjx.hello.netty.chat.proto.InvokServiceGrpc;
import com.fengjx.hello.netty.chat.proto.Request;
import com.fengjx.hello.netty.chat.proto.Response;
import com.fengjx.hello.netty.chat.proto.action.Action;
import io.grpc.stub.StreamObserver;

/**
 * @author fengjianxin
 */
@GrpcService
public class InvokServiceImpl extends InvokServiceGrpc.InvokServiceImplBase {

    @Override
    public void invok(Request request, StreamObserver<Response> responseObserver) {
        String actionName = request.getHeader().getAction();
        Action action = SpringUtils.getBean(actionName, Action.class);
        action.invok(request, responseObserver);
    }
}
