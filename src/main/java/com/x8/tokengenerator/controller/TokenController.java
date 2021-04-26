package com.x8.tokengenerator.controller;

import com.x8.tokengenerator.domain.TokenClaims;
import com.x8.tokengenerator.exceptions.DependencyException;
import com.x8.tokengenerator.service.TokenGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private static final String KID = "KID";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";

    @Autowired
    private Environment environment;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @PostMapping(value = "/token")
    public String getToken(@RequestBody TokenClaims tokenClaims) throws Exception {
        String kid = environment.getProperty(KID);
        String privateKey = environment.getProperty(PRIVATE_KEY);
        if (kid == null) {
            throw new DependencyException("Missing kid");
        }
        if (privateKey == null) {
            throw new DependencyException("Missing private key");
        }
        return tokenGeneratorService.getToken(tokenClaims, kid, privateKey);
    }
}

