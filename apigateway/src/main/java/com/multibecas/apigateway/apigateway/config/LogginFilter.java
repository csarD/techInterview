package com.multibecas.apigateway.apigateway.config;

import com.multibecas.apigateway.apigateway.feignClient.SecurityServiceProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;

@Component
public class LogginFilter implements GatewayFilter {

    private final Logger logger = LogManager.getLogger(LogginFilter.class);
    @Autowired
    private SecurityServiceProxy securityServiceProxy;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("Codigo actualizado");
        logger.info(request.getPath());

        if (!request.getHeaders().containsKey("Authorization")) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            return response.setComplete();
        }

        String headerValue = request.getHeaders().getOrEmpty("Authorization").get(0);
        String token;

        if (headerValue.startsWith("Bearer ")) {
            token = headerValue.split(" ")[1];
        } else {
            token = headerValue;
        }

        try {
            securityServiceProxy.validateToken(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();

            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            return response.setComplete();
        }

        return chain.filter(exchange);
    }
}
