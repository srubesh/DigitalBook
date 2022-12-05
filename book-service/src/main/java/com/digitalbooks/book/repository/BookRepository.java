package com.digitalbooks.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalbooks.book.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query(value = "SELECT b FROM Book b where b.category = ?1 and b.title = ?2 and b.authorId = ?3 and b.price = ?4 and b.publisher = ?5 and b.active = true")
	public Optional<List<Book>> searchBook(String category, String title,int author, double price, String publisher);
	
	public Optional<Book> findById(Long bookId);
	/*
	public Optional<List<Book>> findByCategory(String category);
	public Optional<List<Book>> findByTitle(String title);
	public Optional<List<Book>> findByAuthorId(int author);
	public Optional<List<Book>> findByPrice(double price);
	public Optional<List<Book>> findByPublisher(String publisher);
	*/
	
}
