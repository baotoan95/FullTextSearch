package com.book.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Transactional
public class HibernateSearchConfiguration {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@Bean
	@PersistenceUnit
	FullTextEntityManager fullTextEntityManager() throws InterruptedException {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer().startAndWait();
		return fullTextEntityManager;
	}
}
