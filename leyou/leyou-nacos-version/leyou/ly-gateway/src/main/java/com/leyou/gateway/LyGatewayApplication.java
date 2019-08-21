package com.leyou.gateway;

import com.lh.auto.validcode.annotation.EnableValidCode;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringCloudApplication
@EnableValidCode
@EnableDiscoveryClient
public class LyGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyGatewayApplication.class, args);
    }

}
