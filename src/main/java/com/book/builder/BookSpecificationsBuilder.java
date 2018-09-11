package com.book.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.book.criteria.SearchCriteria;
import com.book.entities.Book;
import com.book.specification.BookSpecification;

public class BookSpecificationsBuilder {
	private final List<SearchCriteria> criterias;
	
	public BookSpecificationsBuilder() {
		this.criterias = new ArrayList<>();
	}
	
	public BookSpecificationsBuilder with(String key, String operation, Object value) {
		criterias.add(new SearchCriteria(key, operation, value));
		return this;
	}
	
	public Specification<Book> build() {
		if(criterias.isEmpty()) {
			return null;
		}
		
		List<Specification<Book>> specs = new ArrayList<>();
		criterias.stream().forEach(criteria -> specs.add(new BookSpecification(criteria)));
		
		Specification<Book> result = specs.get(0);
		for(int i = 1; i < specs.size(); i++) {
			result = Specification.where(result).and(specs.get(i));
		}
		
		return result;
	}
}
