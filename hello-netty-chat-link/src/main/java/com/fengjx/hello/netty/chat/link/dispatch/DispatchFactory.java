package com.fengjx.hello.netty.chat.link.dispatch;

import com.fengjx.hello.netty.chat.link.protobuf.RequestProtos;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author fengjianxin
 */
@Component
public class DispatchFactory {

    private final Map<RequestProtos.ActionType, AbstractDispatch> DISPATCH_MAP = Maps.newHashMap();

    @Resource
    public void setDispatchs(List<AbstractDispatch> dispatchs) {
        dispatchs.forEach(item -> DISPATCH_MAP.put(item.actionType(), item));
    }

    public AbstractDispatch getDispatchByType(RequestProtos.ActionType actionType) {
        return DISPATCH_MAP.get(actionType);
    }

}
