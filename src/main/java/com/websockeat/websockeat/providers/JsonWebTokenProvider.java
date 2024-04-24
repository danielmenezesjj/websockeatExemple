package com.websockeat.websockeat.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.websockeat.websockeat.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;



    @Component
    public class JsonWebTokenProvider implements TokenProvider{

        private final AuthService authService;

        @Autowired
        public JsonWebTokenProvider(AuthService authService) {
            this.authService = authService;
        }

        @Override
        public Map<String, String> decode(String token) {
            try {
                // Decodifica o token JWT
                DecodedJWT jwt = JWT.decode(token);


                String publicKey = authService.authenticate("dan@email.com", "123");


                String[] splitString = publicKey.split("\\.");
                String base64EncodedHeader = splitString[0];
                String base64EncodedBody = splitString[1];
                String base64EncodedSignature = splitString[2];

                // Decodifica o corpo base64
                byte[] decodedBody = Base64.getUrlDecoder().decode(base64EncodedBody);
                String body = new String(decodedBody);

                System.out.println("JWT Body: " + body);


                Map<String, String> tokenInfo = new HashMap<>();
                tokenInfo.put("subject", jwt.getSubject());
                tokenInfo.put("body", body);

                return tokenInfo;

            } catch (Exception e) {
                throw new RuntimeException("Erro ao decodificar o token: " + e.getMessage(), e);
            }
        }
    
    
}
