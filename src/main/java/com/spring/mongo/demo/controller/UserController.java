package com.spring.mongo.demo.controller;

import java.util.List;
import java.util.Optional;
import com.spring.mongo.demo.model.User;
import com.spring.mongo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.mongo.demo.service.UserService;
import org.springframework.web.server.ResponseStatusException;

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
	public ResponseEntity<User> getUserById(@PathVariable String id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}
		return ResponseEntity.notFound().build();
	}

	//create a new connection
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
	@GetMapping("/ref/{refId}")
	public ResponseEntity<List<User>> getAllUsersByRefId(@PathVariable String refId) {
		List<User> users = userService.getAllUserByRefId(refId);

		if (!users.isEmpty()) {
			return ResponseEntity.ok(users);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/ref/{refId}/highest-connection")
	public ResponseEntity<User> getUserWithHighestConnection(@PathVariable String refId) {
		try {
			User userWithHighestConnection = userService.getUserWithHighestConnection(refId);
			return ResponseEntity.ok(userWithHighestConnection);
		} catch (ResponseStatusException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// Handle other exceptions if needed
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}




