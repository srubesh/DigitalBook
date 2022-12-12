package com.digitalbooks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbooks.entities.ERole;
import com.digitalbooks.entities.Role;
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

	public Long findByUserName(String author) {
		Optional<Users> user = repo.findByUsername(author);
		if(user.isPresent()) {
			return user.get().getId();
		}
		return 0L;
	}

	public boolean checkAuthorExists(Long authorId) {
		Optional<Users> user = repo.findById(authorId);
		if(user.isPresent()) {
			Set<Role> roles = user.get().getRoles();
			Set<ERole> roleName = roles.stream()
					.filter(role -> role.getName().equals(ERole.ROLE_AUTHOR))
					.map(role -> role.getName())
					.collect(Collectors.toSet());
			
			if(roleName.size() > 0) {
				return true;
			}
		}
		return false;
	}

	public Optional<Users> findByEmail(String email) {
		Optional<Users> user = repo.findByEmail(email);
		return user;
	}

	public List<Users> getAllAuthors() {
		List<Users> authorList = new ArrayList<Users>();
		List<Users> userList = repo.findAll();
		
		authorList=userList.stream()
				.filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_AUTHOR)))
				.collect(Collectors.toList());
		
		return authorList;
	}
	
	


}
