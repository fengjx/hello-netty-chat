package com.fengjx.hello.netty.chat.logic.rpc;

import com.fengjx.hello.netty.chat.logic.action.ActionFactory;
import com.fengjx.hello.netty.chat.logic.action.ActionMapping;
import com.fengjx.hello.netty.chat.proto.InvokServiceGrpc;
import com.fengjx.hello.netty.chat.proto.Request;
import com.fengjx.hello.netty.chat.proto.Response;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fengjianxin
 */
@Service
public class InvokServiceImpl extends InvokServiceGrpc.InvokServiceImplBase {

    @Resource
    private ActionFactory actionFactory;

    @Override
    public void invok(Request request, StreamObserver<Response> responseObserver) {
        String action = request.getHeader().getAction();
        Response resp;
        if (action != null) {
            ActionMapping mapping = actionFactory.getActionMapping(action);
            resp = mapping.invok(request);
        } else {
            resp = Response.newBuilder().build();
        }
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
