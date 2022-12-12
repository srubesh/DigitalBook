package com.digitalbooks.book.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.book.entities.Book;
import com.digitalbooks.book.payload.response.MessageResponse;
import com.digitalbooks.book.service.BookService;

@RestController
@RequestMapping("digitalbooks")
@CrossOrigin
public class BookController {

	@Autowired
	private BookService bookService;
	

	@GetMapping("/test/{id}")
	public ResponseEntity<?> getBookById(@PathVariable Long id) throws Exception{
		Book book = bookService.getBookById(id);
		
		if(book != null) {
			return ResponseEntity.ok()
					.body(book);
		}
		else {
			return  ResponseEntity.badRequest().body(new MessageResponse("Book does not exist!"));
		}
	}
	
	@GetMapping("/author/{authorId}")
	public ResponseEntity<?> getBookByAuthorId(@PathVariable int authorId) throws Exception{
		Optional<List<Book>> bookList = bookService.getBookByAuthorId(authorId);
		
		if(bookList.isPresent()) {
			return ResponseEntity.ok()
					.body(bookList.get());
		}
		else {
			return  ResponseEntity.badRequest().body(new MessageResponse("Book does not exist!"));
		}
	}

	@PostMapping("/author/{author-id}/books")
//	public ResponseEntity<?> saveBook(@RequestParam("image") MultipartFile file, @ModelAttribute Book book,
//			@PathVariable("author-id") int authorId) {
	public ResponseEntity<?> saveBook(@RequestBody Book book,
			@PathVariable("author-id") int authorId) {
		
		//bookService.saveBook(book);
		//return ResponseEntity.ok().body("Book saved successfully");
		return ResponseEntity.ok().body(bookService.saveBook(book));

	}

	@PostMapping("/author/{author-id}/books/{book-id}")
	public ResponseEntity<?> updateBook(@PathVariable("author-id") int authorId, @PathVariable("book-id") Long bookId,
			@RequestBody Book book) {

	

		//ResponseEntity<?> response = bookService.updateBook( authorId, bookId, book);
		return ResponseEntity.ok().body(bookService.updateBook( authorId, bookId, book));
	}
	
	//author/{author-id}/books/{book-id}?block=yes
	@GetMapping("author/{author-id}/books/{book-id}/{block}")
	public ResponseEntity<MessageResponse> blockBook(@PathVariable("author-id") int authorId,
			@PathVariable("book-id") Long bookId, @PathVariable("block") String block) {
		Optional<Book> book = bookService.checkBookAvailability(bookId);
		if (!book.isPresent()) {
			return ResponseEntity.ok()//status(HttpStatus.NOT_FOUND)
					.body(new MessageResponse("The book does not exist"));
		} else if (book.get().getAuthorId() != authorId) {
			return ResponseEntity.ok()//.status(HttpStatus.NOT_FOUND)
					.body(new MessageResponse("The book does not belong to the mentioned author"));

		} else if (block.equalsIgnoreCase("yes") || block.equalsIgnoreCase("no") ) {
			Book savedBook = bookService.blockBook(book.get(), block);
			return ResponseEntity.ok(new MessageResponse("Book updated successfully"));
		}
		else {
			return ResponseEntity.ok(new MessageResponse("Not a proper instruction"));
		}
	}
	
	//search?category=&title=&author=&price=&publisher=
	/*
	@GetMapping("search")
	public ResponseEntity<?> searchBook(@RequestParam String category, @RequestParam String title,@RequestParam String author,
			@RequestParam String price, @RequestParam String publisher){
		Set<Book> uniqueBook = new HashSet<>();
		
		if(category!="") {
			Optional<List<Book>> bookList = bookService.searchBookByCategory(category);
			if(bookList.isPresent()) {
				bookList.get().stream().forEach(book -> uniqueBook.add(book));
			}
		}
		if(title!="") {
			
			Optional<List<Book>> bookList = bookService.searchBookByTitle(title);
			if(bookList.isPresent()) {
				bookList.get().stream().forEach(book -> uniqueBook.add(book));
			}
		}
		if(author!="") {
			Optional<List<Book>> bookList = bookService.searchBookByAuthor(Integer.valueOf(author));
			if(bookList.isPresent()) {
				bookList.get().stream().forEach(book -> uniqueBook.add(book));
			}
		}
		if(price!="") {
			Optional<List<Book>> bookList = bookService.searchBookByPrice(Double.valueOf(price));
			if(bookList.isPresent()) {
				bookList.get().stream().forEach(book -> uniqueBook.add(book));
			}
		}
		if(publisher!="") {
			Optional<List<Book>> bookList = bookService.searchBookByPublisher(publisher);
			if(bookList.isPresent()) {
				bookList.get().stream().forEach(book -> uniqueBook.add(book));
			}
		}
		
		//Optional<List<Book>> bookList = bookService.searchBook(category,title,author,price,publisher);
		
		if(uniqueBook.size() > 0) {
			return ResponseEntity.ok()
					.body(uniqueBook);
		}
		else {
			return  ResponseEntity.badRequest().body(new MessageResponse("No Result Found"));
		}
	}
	*/

	//search?category=&title=&author=&price=&publisher=
	@GetMapping("search")
	//public ResponseEntity<?> searchBook(@RequestParam String category, @RequestParam String title,@RequestParam String author,
		//	@RequestParam String price, @RequestParam String publisher){
	public List<Book> searchBook(@RequestParam String title,@RequestParam String author,
				@RequestParam String publishedDate, @RequestParam String publisher){
		Optional<List<Book>> bookList = bookService.searchBook(title,Integer.valueOf(author),LocalDate.parse(publishedDate),publisher);
		
		if(bookList.isPresent()) {
//			return ResponseEntity.ok()
//					.body(bookList.get());
			return bookList.get();	
		}
		else {
			//return  ResponseEntity.badRequest().body(new MessageResponse("No Result Found"));
			return null;
		}
	}
}
