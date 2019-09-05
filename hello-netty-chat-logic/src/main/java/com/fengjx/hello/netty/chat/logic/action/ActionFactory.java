package com.fengjx.hello.netty.chat.logic.action;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengjianxin
 */
@Component
public class ActionFactory {


    private Map<String, ActionMapping> actionMapping = new HashMap<>();

    public void registerAction(ActionMapping mapping) {
        actionMapping.put(mapping.getAction(), mapping);
    }

    public ActionMapping getActionMapping(String action) {
        return actionMapping.get(action);
    }

}
