package com.digitalbooks.controller;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.entities.ERole;
import com.digitalbooks.entities.Role;
import com.digitalbooks.entities.Users;
import com.digitalbooks.payload.request.LoginRequest;
import com.digitalbooks.payload.request.SignupRequest;
import com.digitalbooks.payload.response.BookResponse;
import com.digitalbooks.payload.response.JwtResponse;
import com.digitalbooks.payload.response.MessageResponse;
import com.digitalbooks.repository.RoleRepository;
import com.digitalbooks.repository.UserRepository;
import com.digitalbooks.security.jwt.JwtUtils;
import com.digitalbooks.security.service.UserDetailsImpl;
import com.digitalbooks.service.UserService;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@RestController
@RequestMapping("digitalbooks")
@CrossOrigin
public class UserAuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private UserService userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

	String bookUrl = "http://localhost:8082/digitalbooks/";

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> saveUser(@Valid @RequestBody SignupRequest signupRequest) {

		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Users user = new Users(signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()),
				signupRequest.getEmail(), signupRequest.getPhone());

		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_READER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "author":
					Role authorRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(authorRole);
					break;

				default:
					Role readerRole = roleRepository.findByName(ERole.ROLE_READER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(readerRole);
				}
			});
		}

		user.setRoles(roles);

		return userService.saveUser(user);
	}
	
	@GetMapping("all/authors")
	public ResponseEntity<?> getAllAuthors(){
		
		List<Users> authorList = userService.getAllAuthors();
		
		if(!authorList.isEmpty()) {
			return ResponseEntity.ok(authorList);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageResponse("Authors not fount"));
		}
		
	}

	@PostMapping("/author/{author-id}/books")
	public ResponseEntity<?> saveBook(@RequestParam("image") MultipartFile file, @ModelAttribute BookResponse book,
			@PathVariable("author-id") Long authorId) {

		boolean isUserAuthor = userService.checkAuthorExists(authorId);

		if (isUserAuthor) {

			byte[] bytes = null;
			try {

				if (file != null) {
					bytes = file.getBytes();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			
			book.setLogo(bytes);
			book.setAuthorId(authorId);
			book.setPublishedDate(LocalDate.now());

			BookResponse storedBook = restTemplate.postForObject(bookUrl + "/author/" + authorId + "/books", book,
					BookResponse.class);

			if (storedBook != null) {
				return ResponseEntity.ok(new MessageResponse("Book saved successfully"));
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageResponse("User is either not present or user does not have author role"));
		}
	}
	
	
	@PutMapping("/author/{author-id}/books/{book-id}")
	public ResponseEntity<?> updateBook(@RequestParam("image") MultipartFile file,
			@PathVariable("author-id") Long authorId, @PathVariable("book-id") Long bookId, @ModelAttribute BookResponse book) {
			
			boolean isUserAuthor = userService.checkAuthorExists(authorId);

		if (isUserAuthor) {

			byte[] bytes = null;
			try {

				if (file != null) {
					bytes = file.getBytes();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			
			book.setLogo(bytes);
			book.setAuthorId(authorId);
			

			BookResponse updatedBook = restTemplate.postForObject(bookUrl + "/author/" + authorId + "/books/"+bookId, book,
					BookResponse.class);

			if (updatedBook!= null) {
				return ResponseEntity.ok(new MessageResponse("Book update successfully"));
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Book does not exist!"));
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageResponse("User is either not present or user does not have author role"));
		}
	}
	
	@GetMapping("/author/{authorId}")
	public ResponseEntity<?> getBookByAuthorId(@PathVariable Long authorId) throws Exception{
		List<BookResponse> responseBook = restTemplate.getForObject(bookUrl + "/author/" + authorId, List.class);
		
		if(responseBook!=null && !responseBook.isEmpty()) {
			return ResponseEntity.ok()
					.body(responseBook);
		}
		else {
			return  ResponseEntity.badRequest().body(new MessageResponse("Book does not exist!"));
		}
	}

	@GetMapping("/searchBook")
	public ResponseEntity<?> searchBook(@RequestParam("title") String title,
			@RequestParam("author") String author, @RequestParam("publishedDate") String publishedDate,
			@RequestParam("publisher") String publisher) throws Exception {


		List<BookResponse> responseBookList = null;

		Long authoId = userService.findByUserName(author);

		responseBookList = restTemplate.getForObject(bookUrl + "/search?" + "&title=" + title
				+ "&author=" + authoId + "&publishedDate=" + publishedDate + "&publisher=" + publisher, List.class);
		System.out.println("responseBook: " + responseBookList);

		if (responseBookList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book is available for current selection");
		} else {
			return ResponseEntity.ok(responseBookList);
		}

	}

	@PutMapping("author/{author-id}/books/{book-id}/{block}")
	public ResponseEntity<?> blockBook(@PathVariable("author-id") int userId, @PathVariable("book-id") int bookId,
			@PathVariable("block") String block) {
		ResponseEntity<MessageResponse> response = restTemplate
				.getForEntity(bookUrl + "author/" + userId + "/books/" + bookId + "/" + block, MessageResponse.class);
		return response;

	}
	
	@GetMapping("/test/{id}")
	public ResponseEntity<?> getBookById(@PathVariable Long id) throws Exception{
		ResponseEntity<BookResponse> responseBook = restTemplate.getForEntity(bookUrl + "test/" + id, BookResponse.class);
		return responseBook;
	}

}
