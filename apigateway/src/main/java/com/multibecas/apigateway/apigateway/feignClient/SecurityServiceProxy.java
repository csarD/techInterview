package com.multibecas.apigateway.apigateway.feignClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;

@Service
public class SecurityServiceProxy {

    private final Logger logger = LogManager.getLogger(SecurityServiceProxy.class);
    private final RestTemplate restTemplate;

    public SecurityServiceProxy() {
        this.restTemplate = new RestTemplate();
    }

    public void validateToken(String token) throws AuthenticationException {

        boolean isTemporalUser = true;

        try {
            this.restTemplate.postForEntity(
                    "http://security:8120/api/session/validateTemporalToken",
                    token,
                    Void.class);
        } catch (Exception e) {
            logger.error(e);
            isTemporalUser = false;
        }


        if (!isTemporalUser) {
            try {
                this.restTemplate.postForEntity(
                        "http://security:8120/api/session/validateToken",
                        token,
                        Void.class);
            } catch (Exception e) {
                logger.error(e);
                throw new AuthenticationException("Can't validate token");
            }
        }
    }
}
