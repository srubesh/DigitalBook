package com.digitalbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbooks.entities.Users;
import com.digitalbooks.payload.response.MessageResponse;
import com.digitalbooks.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	/*
	 * public static boolean existsByUsername(String username) { return
	 * repo.existsByUsername(username); }
	 * 
	 * public static boolean existsByEmail(String email) { return
	 * repo.existsByEmail(email); }
	 */
	
	public ResponseEntity<?> saveUser(Users user){
		repo.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}



}
