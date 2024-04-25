package com.websockeat.websockeat.handler;


import com.websockeat.websockeat.services.TicketService;
import io.lettuce.core.ScriptOutputType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSockeatHandler extends TextWebSocketHandler {


    private final TicketService ticketService;
    private final Map<String, WebSocketSession> sessions;

    public WebSockeatHandler(TicketService ticketService){
        this.ticketService = ticketService;
        sessions = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)  {
        System.out.println("[afterConnection] session id " + session.getId());
        Optional<String> ticket = ticketOf(session);
        if(ticket.isEmpty() || ticket.get().isBlank()){
            System.out.println("session  " + session.getId() + " without ticket");
            close(session, CloseStatus.POLICY_VIOLATION);
            return;
        }
        Optional<String> userName = ticketService.getUserIdByTicket(ticket.get());
        if(userName.isEmpty()){
            System.out.println("session " + session.getId() + " with invalid ticket");
            close(session, CloseStatus.POLICY_VIOLATION);
            return;
        }
        sessions.put(userName.get(), session);
        System.out.println("session " + session.getId() + " was bind to user " + userName.get());
    }


    private void close(WebSocketSession session, CloseStatus status){
        try{
            session.close(status);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Optional<String> ticketOf(WebSocketSession session){
        return Optional
                .ofNullable(session.getUri())
                .map(UriComponentsBuilder::fromUri)
                .map(UriComponentsBuilder::build)
                .map(UriComponents::getQueryParams)
                .map(it -> it.get("ticket"))
                .flatMap(it -> it.stream().findFirst())
                .map(String::trim);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("[handleTextMessage] message " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)  {
        System.out.println("[afterConnectionClosed] session id " + session.getId());
    }
}
