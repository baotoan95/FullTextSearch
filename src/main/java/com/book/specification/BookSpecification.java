package com.book.specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.book.criteria.SearchCriteria;
import com.book.entities.Book;

@SuppressWarnings("serial")
public class BookSpecification implements Specification<Book> {
	private SearchCriteria criteria;

	public BookSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if(criteria.getOperation().equalsIgnoreCase(">")) {
			return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase("=")) {
			return builder.equal(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase());
		} else if (criteria.getOperation().equalsIgnoreCase("~")) {
			return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			try {
				String[] range = criteria.getValue().toString().split("-");
				Date from = convertStringToDate(range[0]);
				Date to = convertStringToDate(range[1]);
				return builder.between(root.get(criteria.getKey()), from, to);
			} catch (Exception e) {
				// Do nothing
			}
		}
		return null;
	}
	
	private Date convertStringToDate(String date) throws ParseException {
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		return formater.parse(date);
	}
}
