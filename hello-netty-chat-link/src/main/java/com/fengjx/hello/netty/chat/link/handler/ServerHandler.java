package com.fengjx.hello.netty.chat.link.handler;

import com.fengjx.hello.netty.chat.link.auth.Authentication;
import com.fengjx.hello.netty.chat.link.manager.ServerManager;
import com.fengjx.hello.netty.chat.link.protobuf.RequestProtos;
import com.fengjx.hello.netty.chat.link.protobuf.ResponseProtos;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fengjianxin
 */
@Slf4j
@ChannelHandler.Sharable
@Service
public class ServerHandler extends SimpleChannelInboundHandler<RequestProtos.Request> {

    @Resource
    private ServerManager serverManager;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        serverManager.addGroup(ctx.channel());
    }

    private void logOut(RequestProtos.Request request) {
        if (!Authentication.verifyToken(userMapReverse.get(ctx.channel().id()), request.getToken())) {
            response = responseBuilder.setType(ResponseProtos.ResponseType.NOTLOGIN).build();
        } else {
            userName = userMapReverse.get(ctx.channel().id());
            this.userMapReverse.remove(ctx.channel().id());
            this.userMap.remove(userName);
            response = responseBuilder.setType(ResponseProtos.ResponseType.SUCCESS).build();
        }
    }

    private void getMessage(RequestProtos.Request request) {
        if (!Authentication.verifyToken(userMapReverse.get(ctx.channel().id()), request.getHeader().getToken())) {
            response = responseBuilder.setType(ResponseProtos.ResponseType.NOTLOGIN).build();
        } else {
            String storedKey = "user." + userName + ".receive";
            boolean contains = this.storage.contains(storedKey);
            if (contains) {
                int messageNumber = Integer.parseInt(this.storage.get(storedKey));
                System.out.println("so luong mess: " + messageNumber);
                for (int i = 1; i <= messageNumber; i++) {
                    if (!this.storage.contains(storedKey + i)) {
                        continue;
                    }
                    String storedMess = this.storage.get(storedKey + i);
                    storage.remove(storedKey + i);
                    System.out.println(storedKey + String.valueOf(i) + "... " + storedMess);

                    response = responseBuilder.setType(ResponseProtos.ResponseType.MESSAGE)
                            .setMessage(ResponseProtos.Message.newBuilder().setContent(storedMess).build()).setTime(0)
                            .build();
                    ctx.writeAndFlush(response);
                    System.out.printf("Send to [%s]:\n%s\n", ctx.channel().remoteAddress(), response);
                }
                storage.remove(storedKey);
                storage.put(storedKey, String.valueOf(0));
            }
        }
    }

    private void userChat(RequestProtos.Request request) {
        if (!Authentication.verifyToken(userMapReverse.get(ctx.channel().id()), request.getToken())) {
            response = responseBuilder.setType(ResponseProtos.ResponseType.NOTLOGIN).build();
        } else {
            userName = userMapReverse.get(ctx.channel().id());
            String toUser = request.getChat().getTo();
            String content = request.getChat().getMessage();

            if (!this.storage.contains("user." + toUser + ".pass")) {
                response = responseBuilder.setType(ResponseProtos.ResponseType.FAILURE).setMessage(
                        ResponseProtos.Message.newBuilder().setFrom("Server").setContent("User not found!").build())
                        .build();
                ctx.writeAndFlush(response);
                return;
            }

            String key = String.format("message.%s.%s.%d", userName, toUser, System.currentTimeMillis());

            this.storage.put(key, content);

            response = responseBuilder.setType(ResponseProtos.ResponseType.MESSAGE)
                    .setMessage(ResponseProtos.Message.newBuilder().setFrom(userName)
                            .setContent("" + request.getChat().getMessage())

                            .build())
                    .setTime(System.currentTimeMillis()).build();

            if (this.userMap.get(request.getChat().getTo()) != null) {
                Channel channel = this.group.find(this.userMap.get(request.getChat().getTo()));
                System.out.printf("Send to [%s]:\n%s\n", channel.remoteAddress(), response);
                channel.writeAndFlush(response);
            } else {
                String storedKey = "user." + toUser + ".receive";
                int sequenceNumber = 1;
                if (this.storage.contains(storedKey)) {
                    sequenceNumber = Integer.parseInt(this.storage.get(storedKey)) + 1;
                    this.storage.remove(storedKey);
                }
                this.storage.put(storedKey, String.valueOf(sequenceNumber));
                this.storage.put(storedKey + sequenceNumber,
                        new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]").format(System.currentTimeMillis()) + " "
                                + userName + ": " + response.getMessage().getContent());
            }
        }
    }

    private void joinChannel(RequestProtos.Request request) {
        if (!Authentication.verifyToken(userMapReverse.get(ctx.channel().id()), request.getToken())) {
            response = responseBuilder.setType(ResponseProtos.ResponseType.NOTLOGIN).build();
        } else {
            Set<String> channelGroup = groupsChat.get(request.getChannel());
            if (channelGroup == null) {
                channelGroup = ConcurrentHashMap.newKeySet();
                groupsChat.put(request.getChannel(), channelGroup);
            }
            channelGroup.add(userName);

            response = responseBuilder.setType(ResponseProtos.ResponseType.SUCCESS).setMessage(ResponseProtos.Message
                    .newBuilder().setFrom("Server").setContent("Join group successfully!").build()).build();
            ctx.writeAndFlush(response);
            System.out.println("Join successfully");
        }
    }

    private void exitChannel(RequestProtos.Request request) {
        if (!Authentication.verifyToken(userMapReverse.get(ctx.channel().id()), request.getToken())) {
            response = responseBuilder.setType(ResponseProtos.ResponseType.NOTLOGIN).build();
        } else {

            Set<String> channelGroup = groupsChat.get(request.getChannel());
            if (channelGroup != null) {
                channelGroup.remove(userName);
            }
            response = responseBuilder.setType(ResponseProtos.ResponseType.SUCCESS).build();
            ctx.writeAndFlush(response);
            System.out.println("Exit successfully!");
        }
    }

    private void listChannel(RequestProtos.Request request) {
        if (!Authentication.verifyToken(userMapReverse.get(ctx.channel().id()), request.getToken())) {
            response = responseBuilder.setType(ResponseProtos.ResponseType.NOTLOGIN).build();

            ctx.writeAndFlush(response);
        } else {
            System.out.println(groupsChat);
            for (Map.Entry<String, Set<String>> group : groupsChat.entrySet()) {
                if (group.getValue().contains(userName)) {
                    response = responseBuilder.setType(ResponseProtos.ResponseType.MESSAGE).setMessage(
                            ResponseProtos.Message.newBuilder().setFrom("Server").setContent(group.getKey()).build())
                            .build();
                    ctx.writeAndFlush(response);
                }
            }
        }
    }

    private void chatChannel(RequestProtos.Request request) {
        if (!Authentication.verifyToken(userMapReverse.get(ctx.channel().id()), request.getToken())) {

            response = responseBuilder.setHeader(headerbu).build();
        } else {

            String toChannel = request.getChannel();
            String content = request.getChat().getMessage();
            System.out.println("Channel chat: " + userName);
            if (!groupsChat.containsKey(toChannel)) {
                response = responseBuilder.setType(ResponseProtos.ResponseType.FAILURE).setMessage(
                        ResponseProtos.Message.newBuilder().setFrom("Server").setContent("Channel not found!").build())
                        .build();
                ctx.writeAndFlush(response);
                return;
            }
            Set<String> channelGroup = groupsChat.get(toChannel);

            System.out.println(channelGroup);
            System.out.println(groupsChat);
            for (String toUser : channelGroup) {
                if (toUser.equals(userName))
                    continue;

                String key = String.format("message.%s.%s.%d", (request.getChannel() + "_" + userName), toUser,
                        System.currentTimeMillis());
                this.storage.put(key, content);

                response = responseBuilder.setType(ResponseProtos.ResponseType.MESSAGE)
                        .setMessage(ResponseProtos.Message.newBuilder().setFrom(request.getChannel() + "_" + userName)
                                .setContent("" + request.getChat().getMessage()).build())
                        .setTime(System.currentTimeMillis()).build();

                if (this.userMap.get(toUser) != null) {
                    Channel channel = this.group.find(this.userMap.get(toUser));
                    System.out.printf("Send to [%s]:\n%s\n", channel.remoteAddress(), response);
                    channel.writeAndFlush(response);
                } else {

                    String storedKey = "user." + toUser + ".receive";
                    int sequenceNumber = 1;
                    if (this.storage.contains(storedKey)) {
                        sequenceNumber = Integer.parseInt(this.storage.get(storedKey)) + 1;

                        this.storage.remove(storedKey);
                    }
                    this.storage.put(storedKey, String.valueOf(sequenceNumber));
                    this.storage.put(storedKey + sequenceNumber,
                            new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]").format(System.currentTimeMillis()) + " "
                                    + toChannel + "_" + userName + ": " + response.getMessage().getContent());
                }
            }

            System.out.printf("Send to [%s]:\n%s\n", ctx.channel().remoteAddress(), response);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestProtos.Request request) throws Exception {
        log.info("Receive from: [{}]", ctx.channel().remoteAddress());
        log.info("Server received:\n {}", request);
        switch (request.getHeader().getType()) {
        case LOGOUT:
            logOut(request);
            ctx.writeAndFlush(response);
            System.out.println(" logout success: " + ctx.channel().remoteAddress() + response);
            break;
        case CHAT:
            userChat(request);
            System.out.printf("Send to [%s]:\n%s\n", ctx.channel().remoteAddress(), response);
            break;
        case GETMESSAGE:
            getMessage(request);
            break;
        case JOINCHANNEL:
            joinChannel(request);
            break;
        case EXITCHANNEL:
            exitChannel(request);
            break;
        case LISTCHANNEL:
            listChannel(request);
            break;
        case CHATCHANNEL:
            chatChannel(request);
            break;
        default:
            break;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("-------------------------------------------");
        ctx.flush();

    }
}
