package com.digitalbooks.book.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbooks.book.entities.Book;
import com.digitalbooks.book.payload.response.MessageResponse;
import com.digitalbooks.book.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

//	public List<Book> getBooks(){
//		return repo.findAll();
////		return null;
//	}
//	
//
//	public Book getBookById(int id) throws Exception {
//		
//		Optional<Book> optional = repo.findById(id); 
//		if(optional.isPresent()) {
//			return optional.get();
//		} 
//		else {
//			throw new Exception("Can not find book with id: "+id); 
//		}
//		 
////		return null;
//	}
//	
	public Book getBookById(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent())
			return book.get();
		return null;
	}

	public Book saveBook(Book book){
		return bookRepository.save(book);
	}
	
	public Book updateBook(int authorId, Long bookId, Book book) {
		Optional<Book> existingBook=  bookRepository.findById(bookId);
		
		if(existingBook.isPresent() && authorId == existingBook.get().getAuthorId()) {
			existingBook.get().setLogo(book.getLogo());
			existingBook.get().setTitle(book.getTitle());
			existingBook.get().setPrice(book.getPrice());
			existingBook.get().setPublisher(book.getPublisher());
			existingBook.get().setActive(book.isActive());
			existingBook.get().setContent(book.getContent());
			existingBook.get().setLogo(book.getLogo());
			existingBook.get().setCategory(book.getCategory());
//			bookRepository.save(existingBook.get());
//			return ResponseEntity.ok().body("Book is updated successfully");
			return bookRepository.save(existingBook.get());
	
		}
		else {
//			return ResponseEntity.badRequest().body(new MessageResponse("Book does not exist!"));
			return null;
            
		}
		
	}
	
	public Optional<Book> checkBookAvailability(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		return book;
	}
	
	public Book blockBook(Book book, String block) {
		//Optional<Books> book =	bookRepository.findById(bookId);
		
		Book savedBook  = null; 
		if(block.equalsIgnoreCase("yes")) {
			book.setActive(false);
			savedBook = bookRepository.save(book);
		}
		if(block.equalsIgnoreCase("no")) {
			book.setActive(true);
			savedBook = bookRepository.save(book);
		}
		
		return savedBook;
		
	}
	
	public Optional<Set<Book>> searchBook(String title, int author, LocalDate date, String publisher){
		Optional<Set<Book>> bookList = bookRepository.searchBook(title, author, date, publisher);
		return bookList;
	}
	
	public Optional<List<Book>> getBookByAuthorId(int authorId) {
		Optional<List<Book>> bookList = bookRepository.findByAuthorId(authorId);
		
		return bookList;
	}

	
	/*
	public Optional<List<Book>> searchBookByCategory(String category){
		Optional<List<Book>> bookList = bookRepository.findByCategory(category);
		return bookList;
	}
	public Optional<List<Book>> searchBookByTitle(String title){
		Optional<List<Book>> bookList = bookRepository.findByTitle(title);
		return bookList;
	}
	public Optional<List<Book>> searchBookByAuthor(int author){
		Optional<List<Book>> bookList = bookRepository.findByAuthorId(author);
		return bookList;
	}
	public Optional<List<Book>> searchBookByPrice(double price){
		Optional<List<Book>> bookList = bookRepository.findByPrice(price);
		return bookList;
	}
	public Optional<List<Book>> searchBookByPublisher(String publisher){
		Optional<List<Book>> bookList = bookRepository.findByPublisher(publisher);
		return bookList;
	}
	*/

//	public Book deleteBookById(int id) throws Exception {
//		
//		Book book = getBookById(id); 
//		if(book!=null) { 
//			repo.delete(book); 
//			return book; 
//		} 
//		else { 
//			throw new Exception("Can not delete user with id: "+id); 
//		}
//		
////		return null;
//	}
//	
//
//	public Book updateBook(Book b, int id) throws Exception{
////		Book book = getBookById(id);
////		if(book!=null) {
//
//		if(repo.existsById(id)) {
//			b.setId(id); 
//			return repo.save(b); 
//		} 
//		else { 
//			throw new Exception("Can not update book with id: "+id); 
//		}
// 
//		
////		return null;
//	}

}
