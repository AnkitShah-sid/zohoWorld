package com.spring.mongo.demo.service;


import java.util.List;

import com.spring.mongo.demo.model.User;

public interface UserService {
	
	List<User> getAll();
	User saveUser(User user);

	User createUser(String refId, User newUser);

	List<User> getAllUserByRefId(String refId);
	User getUserWithHighestConnection(String refId);

}
