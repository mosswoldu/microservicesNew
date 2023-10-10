package com.microProgram.discoveryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaDiscoveryServerApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaDiscoveryServerApp.class,args);
    }
}
