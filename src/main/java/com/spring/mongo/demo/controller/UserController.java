package com.spring.mongo.demo.controller;

import java.util.List;
import java.util.Optional;
import com.spring.mongo.demo.model.User;
import com.spring.mongo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.mongo.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;


	//save a User
	@PostMapping("/saveUser")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		try {
			User savedUser = userService.saveUser(user);
			return new ResponseEntity<>(savedUser, HttpStatus.OK);
		} catch (Exception e) {
			// Handle exceptions and return an appropriate response
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Get All User ByUserId
	@GetMapping
	public List<User> getAll() {
		return userService.getAll();
	}

	//get User ByUserId
	@GetMapping("/{id}")
	public ResponseEntity<User> getCategoryById(@PathVariable String id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/create")
	public Object createUser(@RequestParam String refId, @RequestBody User newUser) {
		try {
			User createdUser = userService.createUser(refId, newUser);
			return createdUser != null ? createdUser : "Error: User not created";
		} catch (Exception e) {
			e.printStackTrace(); // Log the exception for debugging purposes
			return "Error: An unexpected error occurred";
		}
	}
}




