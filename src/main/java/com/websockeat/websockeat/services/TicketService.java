package com.websockeat.websockeat.services;


import com.websockeat.websockeat.providers.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TokenProvider tokenProvider;

    public String buildAndSaveTicket(String token) {
        if (token == null || token.isBlank()) {
            throw new RuntimeException("Token ausente.");
        }
        Map<String, String> user = tokenProvider.decode(token);
        if (user == null || !user.containsKey("subject")) {
            throw new RuntimeException("Token inválido ou usuário não encontrado.");
        }
        String ticket = UUID.randomUUID().toString();
        String userId = user.get("subject");
        redisTemplate.opsForValue().set(ticket, userId, Duration.ofSeconds(100L));

        return ticket;
    }

    public Optional<String> getUserIdByTicket(String ticket){
        return Optional.ofNullable(redisTemplate.opsForValue().getAndDelete(ticket));
    }



}
