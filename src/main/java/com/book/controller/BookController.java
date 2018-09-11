package com.book.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.config.UserSession;
import com.book.entities.Book;
import com.book.service.BookService;
import com.book.service.Car;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("books")
@Slf4j
public class BookController {
	@Autowired
	private BookService bookService;
	@Autowired
	private UserSession userSession;
	@Autowired
	private Car car;
	
	@Cacheable(value = "books", key = "#query")
	@GetMapping("/search/v1")
	public ResponseEntity<Page<Book>> search(@RequestParam("query") String query, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		log.info(userSession.getAuthor());
		return ResponseEntity.ok(bookService.search(query, page, size));
	}
	
	@GetMapping("/search/v2")
	public ResponseEntity<List<Book>> fullSearch(@RequestParam String searchTerm) throws InterruptedException {
		return ResponseEntity.ok(bookService.search(searchTerm));
	}
	
	@GetMapping
	public ResponseEntity<List<Book>> findByDate(@RequestParam("date") long timstamp) {
		return ResponseEntity.ok(bookService.findByDate(new Date(timstamp)));
	}
	
	@GetMapping("/evictAll")
	@CacheEvict(cacheNames = "books", allEntries = true)
	public void evictAllCache() {
		log.info("Evicted");
	}
	
	@GetMapping("/test")
	public String test() {
		return car.start();
	}
}
