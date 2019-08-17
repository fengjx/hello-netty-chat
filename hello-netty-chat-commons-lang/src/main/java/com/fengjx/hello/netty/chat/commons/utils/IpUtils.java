package com.fengjx.hello.netty.chat.commons.utils;


import cn.hutool.core.net.NetUtil;

import java.util.Set;
import java.util.regex.Pattern;

import static cn.hutool.core.net.NetUtil.LOCAL_IP;


/**
 * @author fengjianxin
 */
public class IpUtils {

    private static final Pattern ipPattern = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\:\\d{1,5}$");

    public static String getLocalInnerIp() {
        Set<String> ips = NetUtil.localIpv4s();
        for (String ip : ips) {
            if (NetUtil.isInnerIP(ip) && !ip.equals(LOCAL_IP)) {
                return ip;
            }
        }
        return ips.iterator().next();
    }

    /**
     * 校验是否合法的IP带端口的格式
     * @param ip 要校验的IP带端口
     * @return 合法返回 true, 否则返回 false
     */
    public static boolean checkIpWithPort(String ip) {
        return ipPattern.matcher(ip).matches();
    }

}
