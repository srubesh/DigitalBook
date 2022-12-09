package com.digitalbooks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalbooks.entities.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

	//public Subscription findByBookId(Long bookId);
	
	public Optional<Subscription> findBySubscriptionId(String subscriptionId);

//	@Query(value = "Select U.id from Users U where U.email=:email",nativeQuery=true)
//	public Optional<Integer> fetchUserByEmail(String email);

//	@Query(value = "Select S from Subscription S where S.USER_ID=:userId and cancelled=0")
//	public Optional<List<Subscription>> fetchSubscriptionByUser(Long userId);

	public Optional<List<Subscription>> findByUserId(Long userId);
}
