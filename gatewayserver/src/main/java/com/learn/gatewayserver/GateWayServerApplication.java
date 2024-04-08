package com.learn.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GateWayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayServerApplication.class, args);
    }

    @Bean
    public RouteLocator yourBankRouteConfig(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/yourbank/accounts/**")
                        .filters(f -> f
                                .rewritePath("/yourbank/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://ACCOUNTS")
                )
                .route(p -> p.path("/yourbank/loans/**")
                        .filters(f -> f
                                .rewritePath("/yourbank/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://LOANS")
                )
                .route(p -> p.path("/yourbank/cards/**")
                        .filters(f -> f
                                .rewritePath("/yourbank/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARDS")
                )
                .build();
    }
}