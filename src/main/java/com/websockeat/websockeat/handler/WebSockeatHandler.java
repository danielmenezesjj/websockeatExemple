package com.websockeat.websockeat.handler;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@Component
public class WebSockeatHandler extends TextWebSocketHandler {



    @Override
    public void afterConnectionEstablished(WebSocketSession session)  {
        System.out.println("[afterConnection] session id " + session.getId());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try{
                    session.sendMessage(new TextMessage("Olá " + UUID.randomUUID()));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }, 4000L, 4000L);
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
