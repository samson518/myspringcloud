package com.mysprindcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class GatewayApplication {
    @Bean
    public RouterFunction<ServerResponse> testFunRouterFunction() {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.path("/test"), request -> ServerResponse.ok()
                        .body(BodyInserters.fromObject("I am testing")));
        return route;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
            return builder.routes().route(r -> r.path("/test/custom").uri("http://ww.baidu.com"))
                .route(r -> r.path("/user/**").uri("lb://user-service"))
                .build();

    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
