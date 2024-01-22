package serg.shamiryan.gatewayserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;


@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class);
    }

    @Bean
    public RouteLocator shamiryanBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/shamiryanbank/accounts/**")
                        .filters(f -> f.rewritePath(
                                        "/shamiryanbank/accounts/(?<segment>.*)",
                                        "/${segment}")
                                .addResponseHeader(
                                        "X-Response-Time",
                                        LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport"))
                        )
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/shamiryanbank/loans/**")
                        .filters(f -> f.rewritePath(
                                        "/shamiryanbank/loans/(?<segment>.*)",
                                        "/${segment}")
                                .addResponseHeader(
                                        "X-Response-Time",
                                        LocalDateTime.now().toString()))
                        .uri("lb://LOANS"))
                .route(p -> p
                        .path("/shamiryanbank/cards/**")
                        .filters(f -> f.rewritePath(
                                        "/shamiryanbank/cards/(?<segment>.*)",
                                        "/${segment}")
                                .addResponseHeader(
                                        "X-Response-Time",
                                        LocalDateTime.now().toString()))
                        .uri("lb://CARDS"))
                .build();
    }
}
