package com.jthink.spring.boot.starter.feign.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 * JThink@JThink
 *
 * @author JThink
 * @version 0.0.1
 * @desc feign httpclient auto configuration
 * @date 2017-06-05 10:10:57
 */
@Configuration
@EnableConfigurationProperties(FeignHttpclientProperties.class)
public class FeignHttpclientAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignHttpclientAutoConfiguration.class);

    @Autowired
    private FeignHttpclientProperties feignHttpclientProperties;

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm;
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("创建SSL连接失败");
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(this.feignHttpclientProperties.getMaxTotal());
        cm.setDefaultMaxPerRoute(this.feignHttpclientProperties.getDefaultMaxPerRoute());
        return cm;
    }

    @Bean
    public HttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
                    @Override
                    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                        long alive = super.getKeepAliveDuration(response, context);
                        if (alive == -1) {
                            // 没有设置keep alive值
                            alive = FeignHttpclientAutoConfiguration.this.feignHttpclientProperties.getAlive();
                        }
                        return alive;
                    }
                })
                .build();
        return httpClient;
    }

}
