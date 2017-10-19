package com.osight.monitor.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

/**
 * @author chenw <a href="mailto:chenw@chsi.com.cn">chen wei</a>
 * @version $Id$
 */
public class ClientChannelMap {
    private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();

    public static void add(String clientId, Channel socketChannel) {
        map.put(clientId, socketChannel);
    }

    public static Channel get(String clientId) {
        return map.get(clientId);
    }

    public static void remove(Channel socketChannel) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() == socketChannel) {
                map.remove(entry.getKey());
            }
        }
    }

    public static void ping() {
        for (Map.Entry<String, Channel> entry : map.entrySet()) {
            entry.getValue().writeAndFlush("0@" + entry.getKey());
        }
    }

}
