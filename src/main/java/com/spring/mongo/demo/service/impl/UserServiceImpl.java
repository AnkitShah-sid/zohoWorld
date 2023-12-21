package com.spring.mongo.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.spring.mongo.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mongo.demo.repository.UserRepository;
import com.spring.mongo.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private UserRepository repository;
    //error logs
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	public User saveUser(User user) {
		return repository.save(user);
	}

	@Override
	public User createUser(String refId, User newUser) {
		Optional<User> existingUserOptional = repository.findById(refId);

		if (existingUserOptional.isPresent()) {
			User existingUser = existingUserOptional.get();

			existingUser.setConnection(existingUser.getConnection() + 1);	// Increment the connection field by 1
			repository.save(existingUser);

			newUser.setRefId(refId);// Set the refId for the new user
			newUser.setConnection(0L);// Initialize the connection field to 0 by default
			// Save the new user
			return repository.save(newUser);
		} else {
			return null;
		}
	}

	@Override
	public List<User> getAll() {
		return repository.findAll();
	}

}


