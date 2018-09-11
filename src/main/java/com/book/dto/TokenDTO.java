package com.book.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO implements Serializable {
	private static final long serialVersionUID = 8155616469884790078L;
	
	private String author;
	private int expiredTime;
	
	public String getAuthor() {
		return author;
	}
	
	public int getExpiredTime() {
		return expiredTime;
	}
}
