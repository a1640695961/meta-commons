package com.meta.redisson.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableAspectJAutoProxy
@ConditionalOnClass({Redisson.class, RedisOperations.class})
@AutoConfigureAfter(RedisAutoConfiguration.class)
@ComponentScan(basePackages = {
        "com.meta.redisson.redisson.lock.aop"
})
public class RedissonAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    public RedissonAutoConfiguration() {
        log.info("###### init RedissonAutoConfiguration success #####");
    }

    @Autowired
    private RedisProperties redisProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson() {
        Config config = null;

        Duration timeoutValue = redisProperties.getTimeout();
        int timeout;
        if (null == timeoutValue) {
            timeout = 10000;
        } else {
            timeout = (int) timeoutValue.toMillis();
        }

        if (!Objects.isNull(redisProperties.getSentinel())) {
            List<String> nodesValue = redisProperties.getSentinel().getNodes();
            String[] nodes = convert(nodesValue);

            config = new Config();
            config.useSentinelServers()
                    .setMasterName(redisProperties.getSentinel().getMaster())
                    .addSentinelAddress(nodes)
                    .setDatabase(redisProperties.getDatabase())
                    .setConnectTimeout(timeout)
                    .setPassword(StringUtils.isEmpty(redisProperties.getPassword()) ? null : redisProperties.getPassword());

        } else if (!Objects.isNull(redisProperties.getCluster())) {

            List<String> nodesValue = redisProperties.getCluster().getNodes();

            String[] nodes = convert(nodesValue);

            config = new Config();
            config.useClusterServers()
                    .addNodeAddress(nodes)
                    .setConnectTimeout(timeout)
                    .setPassword(StringUtils.isEmpty(redisProperties.getPassword()) ? null : redisProperties.getPassword());
        } else {
            config = new Config();
            String prefix = "redis://";

            if (redisProperties.isSsl()) {
                prefix = "rediss://";
            }

            config.useSingleServer()
                    .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setConnectTimeout(timeout)
                    .setDatabase(redisProperties.getDatabase())
                    .setPassword(StringUtils.isEmpty(redisProperties.getPassword()) ? null : redisProperties.getPassword())
                    .setConnectionMinimumIdleSize(redisProperties.getLettuce().getPool().getMinIdle())
                    .setConnectionPoolSize(redisProperties.getLettuce().getPool().getMaxActive());
        }

        return Redisson.create(config);
    }

    private String[] convert(List<String> nodesObject) {
        List<String> nodes = new ArrayList<String>(nodesObject.size());
        for (String node : nodesObject) {
            if (!node.startsWith("redis://") && !node.startsWith("rediss://")) {
                nodes.add("redis://" + node);
            } else {
                nodes.add(node);
            }
        }
        return nodes.toArray(new String[nodes.size()]);
    }

}
