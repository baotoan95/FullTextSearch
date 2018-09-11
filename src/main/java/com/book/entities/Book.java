package com.book.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "books")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Indexed
@AnalyzerDef(name = "FedexTextAnalyzer",
tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), 
filters = {
  @TokenFilterDef(factory = LowerCaseFilterFactory.class),
  @TokenFilterDef(factory = NGramFilterFactory.class)
})
@Analyzer(definition = "FedexTextAnalyzer")
public class Book implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Field(termVector = TermVector.YES)
	private String title;
	@Field(termVector = TermVector.YES)
	private String author;
	private Date publishedDate;
}
