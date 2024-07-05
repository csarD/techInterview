package com.multibecas.apigateway.apigateway.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.unit.DataSize;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class ApiGatewayConfiguration {

    private static final String MULTIBECAS = "lb://clientepersona";
    private static final String SECURITY = "lb://security";
    private static final String PERSONACLIENTE = "lb://evaluation";
    private static final String EVALUATION = "lb://evaluation";
    private static final String FORMULARIO_SERVICE = "lb://formulario-service";
    private static final String FILE_GENERATOR = "lb://file-generator";
    private static final String BATCH_PROCESS = "lb://batch-process";

    private static final String NOTIFICATIONS = "lb://notifications";

    @Autowired
    private LogginFilter loggingFilter;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                //Rutas abiertas
                .route(p -> p.path("/persona/**").filters(f -> f.stripPrefix(1)).uri("http://192.168.0.103:8130"))
                .route(p -> p.path("/multibecas/api/expedientes/checkCedulaExists/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/security/api/session").filters(f -> f.stripPrefix(1)).uri(SECURITY + "/api/session"))
                .route(p -> p.path("/security/api/users/identificacion/**").filters(f -> f.stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/security/api/tipoDoc/findAll").filters(f -> f.stripPrefix(1)).uri(SECURITY + "/api/tipoDoc/findAll"))
                .route(p -> p.path("/multibecas/api/promocion/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/multibecas/api/file/download/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/multibecas/api/promocion/findActiveOrNextActive/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/multibecas/api/beca").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS + "/api/beca"))
                .route(p -> p.path("/multibecas/api/beca/nombreBeca").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS + "/api/beca"))
                .route(p -> p.path("/multibecas/api/beca/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/formulario-service/api/formularios/**").filters(f -> f.stripPrefix(1)).uri(FORMULARIO_SERVICE))
                .route(p -> p.path("/formulario-service/api/respuestas/**").filters(f -> f.stripPrefix(1)).uri(FORMULARIO_SERVICE))
                .route(p -> p.path("/multibecas/api/workflow/getTemporalSolicitud/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/multibecas/api/workflow/confirmPsicologicEvaluationFromStudent/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/multibecas/api/workflow/confirmInterviewFromStudent/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/multibecas/api/workflow/confirmAcademicEvaluationFromStudent/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/security/api/token/validateTokenForMissingNotification/**").filters(f -> f.stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/security/api/token/validateTokenForPrivatePromotion/**").filters(f -> f.stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/security/api/users/updateNewPassword/**").filters(f -> f.stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/security/api/users/generatePasswordUpdateCode/**").filters(f -> f.stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/security/api/users/validatePasswordUpdateCode/**").filters(f -> f.stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/security/api/users/updatePassword/**").filters(f -> f.stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/security/api/usuarioTemporal").filters(f -> f.stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/security/api/token/validateTokenForPrivateBeca/**").filters(f -> f.stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/multibecas/api/becasPrivadas/validateCedulaForPromotion").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/multibecas/api/workflow/verifyIntendificationPrivateBeca/**").filters(f -> f.stripPrefix(1)).uri(MULTIBECAS))
                //Rutas que necesitan login
                .route(p -> p.path("/multibecas/**").filters(f -> f.filter(loggingFilter).stripPrefix(1)).uri(MULTIBECAS))
                .route(p -> p.path("/evaluation/**").filters(f -> f.filter(loggingFilter).stripPrefix(1)).uri(EVALUATION))
                .route(p -> p.path("/security/**").filters(f -> f.filter(loggingFilter).stripPrefix(1)).uri(SECURITY))
                .route(p -> p.path("/formulario-service/**").filters(f -> f.filter(loggingFilter).stripPrefix(1)).uri(FORMULARIO_SERVICE))
                .route(p -> p.path("/file-generator/**").filters(f -> f.filter(loggingFilter).stripPrefix(1)).uri(FILE_GENERATOR))
                .route(p -> p.path("/notifications/**").filters(f -> f.filter(loggingFilter).stripPrefix(1)).uri(NOTIFICATIONS))
                .route(p -> p.path("/batch-process/**").filters(f -> f.filter(loggingFilter).stripPrefix(1)).uri(BATCH_PROCESS))
                .build();
    }

    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();

            HttpHeaders headers = response.getHeaders();

            // X-Content-Type-Options: Evita que el navegador interprete archivos incorrectamente
            headers.add("X-Content-Type-Options", "nosniff");
            // Strict-Transport-Security: Establece políticas de seguridad para HTTPS
            headers.add("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
            // X-Frame-Options: Evita el clickjacking
            headers.add("X-Frame-Options", "DENY");
            // Referrer-Policy: Controla la información de referencia en las solicitudes
            headers.add("Referrer-Policy", "strict-origin-when-cross-origin");
            // Cross-Origin-Embedder-Policy: Controla la política de seguridad de embebimiento (configúrala según tus necesidades)
            headers.add("Cross-Origin-Embedder-Policy", "*");
            // Cross-Origin-Resource-Policy: Controla cómo se comparten los recursos entre diferentes orígenes (configúralo según tus necesidades)
            headers.add("Cross-Origin-Resource-Policy", "*");
            // Permissions-Policy: Configura permisos para características del navegador (configúralos según tus necesidades)
            headers.add("Permissions-Policy", "accelerometer=(), camera=(), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), payment=(), usb=() img-src 'self'; default-src 'self'");
            // Content-Security-Policy: Ejemplo de configuración más restrictiva
            headers.add("Content-Security-Policy", "default-src 'self'; script-src 'self' 'unsafe-inline'");

            return chain.filter(exchange);
        };
    }



    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // Establece el tamaño máximo permitido para el cuerpo de la solicitud
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        return factory.createMultipartConfig();
    }

}