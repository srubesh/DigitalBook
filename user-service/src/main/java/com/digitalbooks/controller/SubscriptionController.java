package com.digitalbooks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.digitalbooks.entities.Subscription;
import com.digitalbooks.entities.Users;
import com.digitalbooks.payload.request.SubscriptionPayLoad;
import com.digitalbooks.payload.response.BookContentResponse;
import com.digitalbooks.payload.response.BookResponse;
import com.digitalbooks.payload.response.MessageResponse;
import com.digitalbooks.service.SubscriptionService;
import com.digitalbooks.service.UserService;

@RestController
@RequestMapping(value = "/digitalbooks")
public class SubscriptionController {

	@Autowired
	SubscriptionService subscriptionService;

	@Autowired
	UserService userService;

	@Autowired
	RestTemplate restTemplate;

	String bookUrl = "http://localhost:8082/digitalbooks/";

	@PostMapping("/{book-id}/subscribe")
	public ResponseEntity<?> subscribe(@PathVariable("book-id") Long bookId,
			@RequestBody SubscriptionPayLoad subscribe) {
		BookResponse responseBook = null;
		Users resultUser = null;

		// boolean isUserExist = subscriptionService.checkUserExists(subscribe);
		// boolean isBookExist = subscriptionService.checkBookExists(bookId);
		Optional<Users> user = userService.findByEmail(subscribe.getEmail());
		if (user.isPresent())
			resultUser = user.get();

		try {
			responseBook = restTemplate.getForObject(bookUrl + "/test/" + bookId, BookResponse.class);
		} catch (Exception ex) {
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("There is some issue in fetching book with mentioned id"));
		}

		if (responseBook != null) {
			if (resultUser != null) {
				// boolean isDuplicate = subscriptionService.checkduplicateSubscription(bookId,
				// resultUser.getId());
				Subscription subscription = subscriptionService
						.fetchSubscriptionByIdForSubscribe( resultUser.getId() + "_" +bookId);
				if ((subscription == null) || (subscription != null && subscription.isCancelled())) {
					boolean isReader = subscriptionService.checkUser(subscribe);
					if (isReader) {
						if (subscription == null) {

							ResponseEntity<?> response = subscriptionService.subscribe(responseBook.getId(),
									resultUser.getId(), subscribe);
							return response;

						} else {
							ResponseEntity<?> response = subscriptionService.subscribe(subscription);
							return response;
						}
					} else {
						return ResponseEntity.badRequest()
								.body(new MessageResponse("User does not exist with Reader Role"));
					}
				} else {
					return ResponseEntity.badRequest()
							.body(new MessageResponse("User has already subscribed for this book"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found");
			}
		}

		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageResponse("Book is not found to subscribe"));
		}

	}

	/** Reader can fetch all subscribed books **/

	@GetMapping("readers/{emailId}/books")
	public ResponseEntity<?> getSubscribedBooks(@PathVariable("emailId") String email) throws Exception {
		BookResponse responseBook = null;
		Users resultUser = null;
		Optional<Users> user = userService.findByEmail(email);
		if (user.isPresent())
			resultUser = user.get();
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found");
		}
		Optional<List<Subscription>> subscriptionList = subscriptionService
				.fetchSubscribedBooksForUser(resultUser.getId());
		List<BookResponse> listofBooks = new ArrayList<>();
		if (subscriptionList.isPresent() && !subscriptionList.get().isEmpty()) {

			for (int i = 0; i < subscriptionList.get().size(); i++) {
				Long bookId = subscriptionList.get().get(i).getBookId();

				responseBook = restTemplate.getForObject(bookUrl + "/test/" + bookId, BookResponse.class);

				try {
					listofBooks.add(responseBook);
				} catch (Exception ex) {
					throw new Exception(ex.getMessage());
				}
				if (responseBook == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("No book is available for current selection");
				}
			}

			return ResponseEntity.ok(listofBooks);
		}

		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No subscription found");
		}
	}

	/**** Reader can fetch a subscribe book ****/

	@GetMapping("readers/{emailId}/books/{subscription-id}")
	public ResponseEntity<?> fetchBookContentBySubscriptionId(@PathVariable("emailId") String email,
			@PathVariable("subscription-id") String subscriptionId) throws Exception {
		BookResponse responseBook = null;
		Users resultUser = null;

		Optional<Users> user = userService.findByEmail(email);
		if (user.isPresent()) {
			resultUser = user.get();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found");
		}

		Subscription subscription = subscriptionService.fetchSubscriptionById(subscriptionId);

		if (subscription == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription is not found");
		}

		if (subscription.getUserId() != resultUser.getId()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("The user does not have subscription for this book");
		} else {

			responseBook = restTemplate.getForObject(bookUrl + "/test/" + subscription.getBookId(), BookResponse.class);

			if (responseBook == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book is available for current selection");
			} else {
				return ResponseEntity.ok(responseBook);
			}

		}

	}

	/**** Reader can read book content *****/
	@GetMapping("readers/{emailId}/books/{subscription-id}/read")
	public ResponseEntity<?> fetchBookContent(@PathVariable("emailId") String email,
			@PathVariable("subscription-id") String subscriptionId) throws Exception {

		BookResponse responseBook = null;
		Users resultUser = null;

		Optional<Users> user = userService.findByEmail(email);
		if (user.isPresent()) {
			resultUser = user.get();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found");
		}

		Subscription subscription = subscriptionService.fetchSubscriptionById(subscriptionId);

		if (subscription == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription is not found");
		}
		if (subscription.getUserId() != resultUser.getId()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("The user does not have subscription for this book");
		} else {

			responseBook = restTemplate.getForObject(bookUrl + "/test/" + subscription.getBookId(),
					BookResponse.class);

			if (responseBook.getId() == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book is available for current selection");
			} else if (!responseBook.isActive()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This book is blocked");
			} else {
				BookContentResponse bookContentResponse = new BookContentResponse();
				bookContentResponse.setTitle(responseBook.getTitle());
				bookContentResponse.setContent(responseBook.getContent());
				bookContentResponse.setActive(responseBook.isActive());

				return ResponseEntity.ok(bookContentResponse);
			}

		}

	}

	@PutMapping("readers/{email-id}/books/{subscription-id}/cancel-subscription")
	public ResponseEntity<?> cancelSubscription(@PathVariable("email-id") String email,

			@PathVariable("subscription-id") String subscriptionId) {

		Optional<Users> user = userService.findByEmail(email);
		Users resultUser = null;
		if (user.isPresent()) {
			resultUser = user.get();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found");
		}

		Subscription subscription = subscriptionService.fetchSubscriptionById(subscriptionId);

		if (subscription == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription is not found");
		}
		
		if (subscription.getUserId() != resultUser.getId()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("The user does not have subscription for this book");
		} else {
			ResponseEntity<?> subscriptionCancellation = subscriptionService.cancelSubscription(subscriptionId);
			return subscriptionCancellation;
		}

	}

}
