package com.fengjx.hello.netty.chat.link.proto;

import com.fengjx.hello.netty.chat.proto.InvokServiceGrpc;
import com.fengjx.hello.netty.chat.proto.Request;
import com.fengjx.hello.netty.chat.proto.Response;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author fengjianxin
 */
@Slf4j
public class InvokServiceClient {

    private final InvokServiceGrpc.InvokServiceStub stub;

    public InvokServiceClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    private InvokServiceClient(ManagedChannel channel) {
        stub = InvokServiceGrpc.newStub(channel);
    }

    public void invok(Request request, Consumer<Response> consumer) {
        stub.incok(request, new StreamObserver<Response>() {
            @Override
            public void onNext(Response response) {
                consumer.accept(response);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    public void invok(Request request, Consumer<Response> consumerResponse, Consumer<Throwable> consumerThrowable) {
        stub.incok(request, new StreamObserver<Response>() {
            @Override
            public void onNext(Response response) {
                consumerResponse.accept(response);
            }

            @Override
            public void onError(Throwable throwable) {
                consumerThrowable.accept(throwable);
            }

            @Override
            public void onCompleted() {

            }
        });
    }

}
