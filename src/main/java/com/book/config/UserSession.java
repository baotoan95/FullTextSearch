package com.book.config;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.book.dto.TokenDTO;
import com.book.exceptions.AuthenticationException;
import com.book.utils.TokenUtils;

import lombok.extern.slf4j.Slf4j;

@RequestScope
@Component
@Slf4j
public class UserSession {
	@Autowired
	private HttpServletRequest httpRequest;
	
	private String author;
	
	@PostConstruct
	public void init() throws AuthenticationException {
		String authToken = this.httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
		TokenDTO tokenDTO = TokenUtils.parseToken(authToken);
		
		if(Objects.nonNull(tokenDTO)) {
			this.author = tokenDTO.getAuthor();
			log.info("Author: {}", this.author);
		} else {
			log.error("Can't get authentication token");
			throw new AuthenticationException("Access denied");
		}
	}
	
	public String getAuthor() {
		return author;
	}
}
