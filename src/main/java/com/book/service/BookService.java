package com.book.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.book.builder.BookSpecificationsBuilder;
import com.book.entities.Book;
import com.book.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private FullTextEntityManager fullTextEntityManager;

	public Page<Book> search(String query, int page, int size) {
		BookSpecificationsBuilder builder = new BookSpecificationsBuilder();
		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|=|~)(\\w+?),");
        Matcher matcher = pattern.matcher(query + ",");
        while(matcher.find()) {
        	builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        
        return bookRepository.findAll(builder.build(), PageRequest.of(page, size));
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Book> search(String searchTerm) throws InterruptedException {
		fullTextEntityManager.createIndexer().startAndWait();
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Book.class).get();
        Query luceneQuery = qb.keyword()
        		.onFields("title", "author")
                .matching(searchTerm).createQuery();
 
        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);
 
        // execute search
        List<Book> employeeList = null;
        try {
            employeeList  = jpaQuery.getResultList();
        } catch (NoResultException nre) {
        	employeeList = Collections.emptyList();
        }
 
        return employeeList;
	}
	
	public List<Book> findByDate(Date date) {
		return bookRepository.findByDate(date);
	}
}
