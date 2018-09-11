package com.book.utils;

import java.util.Objects;

import org.apache.tomcat.util.codec.binary.Base64;

import com.book.dto.TokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TokenUtils {
	private TokenUtils() {

	}

	public static TokenDTO parseToken(String token) {
		if (Objects.nonNull(token)) {
			try {
				String[] splittedtString = token.split("\\.");
				String base64EncodedBody = splittedtString[1];

				Base64 base64 = new Base64(true);
				String jsonBody = new String(base64.decode(base64EncodedBody));

				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.readValue(jsonBody, TokenDTO.class);
			} catch (Exception e) {
				log.error("{}", e);
			}
		}

		return null;
	}
}
