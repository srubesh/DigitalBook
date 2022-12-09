package com.digitalbooks.book.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalbooks.book.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query(value = "SELECT b FROM Book b where b.title = ?1 and b.authorId = ?2 and b.publishedDate = ?3 and b.publisher = ?4 and b.active = true")
	public Optional<List<Book>> searchBook(String title,int author, LocalDate date, String publisher);
	
	public Optional<Book> findById(Long bookId);
	/*
	public Optional<List<Book>> findByCategory(String category);
	public Optional<List<Book>> findByTitle(String title);
	public Optional<List<Book>> findByAuthorId(int author);
	public Optional<List<Book>> findByPrice(double price);
	public Optional<List<Book>> findByPublisher(String publisher);
	*/
	
}
