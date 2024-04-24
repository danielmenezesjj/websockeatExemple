package com.websockeat.websockeat.cachetest;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class TimeLoop {

    @Autowired StringGenerator stringGenerator;

    @PostConstruct
    void init(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               String simple = stringGenerator.buildSimpleString();
               String cached = stringGenerator.buildCachedString();
                System.out.println("simple: " + simple + ", cached: " + cached);
            }
        }, 2000L, 2000L);
    }
}
