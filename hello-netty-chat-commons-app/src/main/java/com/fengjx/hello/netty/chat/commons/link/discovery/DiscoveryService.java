package com.fengjx.hello.netty.chat.commons.link.discovery;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.springframework.stereotype.Component;

import java.io.Closeable;

/**
 * @author fengjianxin
 */
@Slf4j
@Component
public class DiscoveryService implements Closeable {

    private CuratorFramework client;
    private AsyncCuratorFramework asyncClient;
    private ServiceDiscovery<LinkServerInstance> serviceDiscovery;


    private DiscoveryProperties properties;

    public DiscoveryService(DiscoveryProperties properties) throws Exception {
        this.properties = properties;
        this.init();
    }

    public void register() throws Exception {
        LinkServerInstance instance = properties.getInstance();
        if (StringUtils.isBlank(instance.getHost())) {
            throw new RuntimeException("没有找到link instance配置信息，可查看DiscoveryProperties");
        }
        // 构造服务信息
        ServiceInstance<LinkServerInstance> thisInstance = ServiceInstance.<LinkServerInstance>builder().name(properties.getRegisterName())
                .address(instance.getHost()).port(instance.getPort()).id(instance.getId())
                .payload(instance).build();
        // 服务信息json序列化器
        JsonInstanceSerializer<LinkServerInstance> serializer = new JsonInstanceSerializer<>(
                LinkServerInstance.class);
        // 创建服务发现对象（用户注册，查询，修改，删除服务）
        serviceDiscovery = ServiceDiscoveryBuilder.builder(LinkServerInstance.class).client(client).basePath(properties.getRegisterPath())
                .serializer(serializer).thisInstance(thisInstance).watchInstances(true).build();
        serviceDiscovery.start();
        log.info("register link instance: {}", instance);
    }


    @Override
    public void close() {
        // 服务关闭，将节点数据删除，并断开与zookeeper连接
        CloseableUtils.closeQuietly(serviceDiscovery);
        asyncClient.unwrap();
        client.close();
        log.info("discoveryService close");
    }

    public void init() throws Exception {
        client = CuratorFrameworkFactory.newClient(properties.getZkConnect(), new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries()));
        client.start();
        client.blockUntilConnected();
        asyncClient = AsyncCuratorFramework.wrap(client);
        log.info("discoveryService start");
    }


}
