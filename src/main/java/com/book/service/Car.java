package com.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
	@Autowired
    private Engine engine;
    @Autowired
    private Transmission transmission;
    
    public String start() {
    	return engine.getEngine() + " " + transmission.getTransission();
    }
}
