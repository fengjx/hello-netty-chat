package com.fengjx.hello.netty.chat.link.dispatch;

import com.fengjx.hello.netty.chat.proto.RequestType;
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

    private final Map<RequestType, AbstractDispatch> DISPATCH_MAP = Maps.newHashMap();

    @Resource
    public void setDispatchs(List<AbstractDispatch> dispatchs) {
        dispatchs.forEach(item -> DISPATCH_MAP.put(item.type(), item));
    }

    public AbstractDispatch getDispatchByType(RequestType type) {
        return DISPATCH_MAP.get(type);
    }

}
