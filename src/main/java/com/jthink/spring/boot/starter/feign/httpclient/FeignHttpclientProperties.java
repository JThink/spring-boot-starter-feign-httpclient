package com.jthink.spring.boot.starter.feign.httpclient;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JThink@JThink
 *
 * @author JThink
 * @version 0.0.1
 * @desc 配置
 * @date 2017-06-05 10:10:57
 */
@ConfigurationProperties(prefix = "spring.cloud.feign.httpclient")
public class FeignHttpclientProperties {

    private int maxTotal;
    private int defaultMaxPerRoute;
    private long alive;

    public int getMaxTotal() {
        return maxTotal;
    }

    public FeignHttpclientProperties setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public FeignHttpclientProperties setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        return this;
    }

    public long getAlive() {
        return alive;
    }

    public FeignHttpclientProperties setAlive(long alive) {
        this.alive = alive;
        return this;
    }
}
