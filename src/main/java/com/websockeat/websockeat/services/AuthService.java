package com.websockeat.websockeat.services;



import com.websockeat.websockeat.authProviders.AuthRequest;
import com.websockeat.websockeat.authProviders.AuthResponse;
import com.websockeat.websockeat.authProviders.AuthUserClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthUserClientApi authUserClient;

    @Autowired
    public AuthService(AuthUserClientApi authUserClient) {
        this.authUserClient = authUserClient;
    }

    public String authenticate(String email, String password) {
        AuthRequest authRequest = new AuthRequest(email, password);
        AuthResponse response = authUserClient.authenticate(authRequest);
        return response.getToken();
    }

}
