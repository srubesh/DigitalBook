package com.digitalbooks.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbooks.entities.ERole;
import com.digitalbooks.entities.Subscription;
import com.digitalbooks.entities.Users;
import com.digitalbooks.payload.request.SubscriptionPayLoad;
import com.digitalbooks.payload.response.MessageResponse;
import com.digitalbooks.repository.SubscriptionRepository;
import com.digitalbooks.repository.UserRepository;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<?> subscribe(Long bookId, Long userId, SubscriptionPayLoad subscribe) {
//       if(true) {

		// Users user =userRepository.findByEmail(subscribe.getEmail());

		Subscription subscription = new Subscription();
		// subscription.setId(UUID.randomUUID().toString());
		subscription.setBookId(bookId);
		subscription.setUserId(userId);
		// subscription.setCancelled(false);
		// subscription.setDateOfCancellation(null);
		subscription.setSubscriptionId(userId + "_" + bookId);
		subscription.setDateOfSubscription(new Date());
		subscription.setCancelled(false);
		subscription.setDateOfCancellation(null);
		Subscription savedSubscription = subscriptionRepository.save(subscription);

		return ResponseEntity.ok(new MessageResponse(
				"Subscription is successfully added with subscription id: " + savedSubscription.getSubscriptionId()));
//		}
//		else {
//			return ResponseEntity.badRequest().body(new MessageResponse("Book does not exist"));
//		}
	}

	public ResponseEntity<?> subscribe(Subscription subscription) {
//      if(true) {

		// Users user =userRepository.findByEmail(subscribe.getEmail());

		// Subscription subscription = new Subscription();
		// subscription.setId(UUID.randomUUID().toString());
//   		   subscription.setBookId(bookId);
//   		   subscription.setUserId(userId);
		// subscription.setCancelled(false);
		// subscription.setDateOfCancellation(null);
//   		   subscription.setSubscriptionId(bookId+"_"+userId);
//   		   subscription.setDateOfSubscription(new Date());
		//subscription.setId(subscription.getId());
		subscription.setCancelled(false);
		subscription.setDateOfCancellation(null);
		Subscription savedSubscription = subscriptionRepository.save(subscription);

		return ResponseEntity.ok(new MessageResponse(
				"Subscription is successfully added with subscription id: " + savedSubscription.getSubscriptionId()));
//		}
//		else {
//			return ResponseEntity.badRequest().body(new MessageResponse("Book does not exist"));
//		}
	}

	public boolean checkUser(SubscriptionPayLoad subscribe) {
		Optional<Users> user = userRepository.findByEmail(subscribe.getEmail());
		boolean isReader = false;
		if (user.isPresent())
			isReader = user.get().getRoles().stream().anyMatch(role -> role.getName() == ERole.ROLE_READER);
		return isReader;

	}

	public boolean checkduplicateSubscription(Long bookId, Long userId) {
		// Subscription subscription = null;

		String subId = userId + "_" + bookId;
		Optional<Subscription> subscription = subscriptionRepository.findBySubscriptionId(subId);

		if (subscription.isPresent()) {
			if (!subscription.get().isCancelled()) {
				return true;
			} else {
				return false;
			}
		}

		return false;

	}

	public Optional<List<Subscription>> fetchSubscribedBooksForUser(Long userId) {
		Optional<List<Subscription>> subscriptionList = subscriptionRepository.findByUserId(userId);

		return subscriptionList;

	}

//	public void fetchBookContentBySubscriptionId(String email, String subscriptionId) {
//		Users user =userRepository.findByEmail(email);
//		int userId= user.getId();
//		Optional<Subscription> subscription = subscriptionRepository.findById(subscriptionId);
//		
//		if(!subscription.isEmpty()) {
//			if(subscription.get().getUser().getId()==userId) {
//					
//				
//			}
//			else {
//				//not a subscriber
//			}
//		}
//		else {
//			
//		}
//		
//	}

	public Subscription fetchSubscriptionById(String subscriptionId) {
		Optional<Subscription> subscription = subscriptionRepository.findBySubscriptionId(subscriptionId);

		if (subscription.isPresent()) {
			if (!subscription.get().isCancelled()) {
				return subscription.get();
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	public Subscription fetchSubscriptionByIdForSubscribe(String subscriptionId) {
		Optional<Subscription> subscription = subscriptionRepository.findBySubscriptionId(subscriptionId);

		if (subscription.isPresent()) {
			return subscription.get();
		} else {
			return null;
		}

	}

	public ResponseEntity<MessageResponse> cancelSubscription(String subscriptionId) {
		Optional<Subscription> subscription = subscriptionRepository.findBySubscriptionId(subscriptionId);
		if (subscription.isPresent()) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, -24);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date twentyfourHoursAgo = calendar.getTime();
			if (subscription.get().getDateOfSubscription().before(twentyfourHoursAgo)) {
				return ResponseEntity.ok(new MessageResponse("Cancellation duration over"));
			} else {
				subscription.get().setCancelled(true); // cancelling subscription
				subscription.get().setDateOfCancellation(new Date());
				subscriptionRepository.save(subscription.get());
				return ResponseEntity.ok(new MessageResponse("Cancel request processed"));
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("NO Subscription is found"));
		}

	}

}
