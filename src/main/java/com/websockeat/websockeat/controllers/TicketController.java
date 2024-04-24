package com.websockeat.websockeat.controllers;


import com.websockeat.websockeat.providers.JsonWebTokenProvider;
import com.websockeat.websockeat.services.AuthService;
import com.websockeat.websockeat.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("v1/ticket")
@CrossOrigin
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private JsonWebTokenProvider jsonWebTokenProvider;

    @Autowired
    private  AuthService authService;




    @PostMapping
    public Map<String, String> buildTicket(){
        String ticket = ticketService.buildAndSaveTicket(authService.authenticate("dan@email.com", "123"));
        return Map.of("ticket", ticket);
    }



}
