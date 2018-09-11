package com.book.criteria;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria implements Serializable {
	private String key;
	private String operation;
	private Object value;
}
