package com.fengjx.hello.netty.chat.link.mq;

import com.fengjx.hello.netty.chat.link.config.MqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqReceiver {

    @RabbitListener(queues = { MqConfig.QUEUE_NAME })
    public void action(String jsonMsg) {

    }

}
