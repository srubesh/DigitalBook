package com.digitalbooks.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/reader")
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR')")
	public String userAccess() {
		return "READER Content.";
	}

	@GetMapping("/author")
	@PreAuthorize("hasRole('AUTHOR')")
	public String moderatorAccess() {
		return "AUTHOR Board.";
	}
}
