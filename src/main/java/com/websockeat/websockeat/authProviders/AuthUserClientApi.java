package com.websockeat.websockeat.authProviders;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:9005/public/auth", name = "auth")
public interface AuthUserClientApi {

    @PostMapping
    AuthResponse authenticate(@RequestBody AuthRequest authRequest);

}
