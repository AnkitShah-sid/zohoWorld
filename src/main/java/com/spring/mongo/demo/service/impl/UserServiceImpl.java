package com.spring.mongo.demo.service.impl;

import java.util.*;

import com.spring.mongo.demo.model.Allconnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.spring.mongo.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.spring.mongo.demo.repository.UserRepository;
import com.spring.mongo.demo.service.UserService;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {


	private static final double OTHER_PROFIT_PERCENTAGE = 0.05;
	private static final double LONG_LEG_PROFIT_PERCENTAGE = 0.10;


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
			newUser.setIncomeList(new ArrayList<>());
			newUser.setProfitIncome(new ArrayList<>());
			// Save the new user
			return repository.save(newUser);
		} else {
			return null;
		}
	}

	@Override
	public List<User> getAllUserByRefId(String refId) {
		return repository.getAllUserByRefId(refId);
	}

	@Override
	public User getUserWithHighestConnection(String refId) {
		List<User> users = getAllUserByRefId(refId);



		if (!users.isEmpty()) {
			// Find the user with the highest connection count
			User userWithHighestConnection = users.stream()
					.max(Comparator.comparingLong(User::getConnection))
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found"));

			// Calculate total investment for all users
			double totalInvestmentForAllUsers = users.stream()
					.mapToDouble(User::getInvestment)
					.sum();

			// Subtract the investment of the user with the highest connection count
			double totalInvestmentWithoutHighestConnection = totalInvestmentForAllUsers - userWithHighestConnection.getInvestment();

			// Calculate investments based on percentages
			double otherProfit = totalInvestmentWithoutHighestConnection * OTHER_PROFIT_PERCENTAGE;
			double longLegProfit = userWithHighestConnection.getInvestment() * LONG_LEG_PROFIT_PERCENTAGE;

			Optional<User> userOptional = repository.findById(refId);
			if (userOptional.isPresent()) {
				User user = userOptional.get();

				// Create Allconnection object
				Allconnection allconnection = new Allconnection();
				allconnection.setOtherProfit(otherProfit);
				allconnection.setLongLegProfit(longLegProfit);

				// Get the existing list of connections from the user
				List<Allconnection> connections = user.getProfitIncome();

				// Add the new connection to the list
				connections.add(allconnection);

				// Set the updated list of connections in the user
				user.setProfitIncome(connections);

				// Save or update the user with the updated list of connections
				repository.save(user);

			}
				return userWithHighestConnection;

		} else {
			// Handle the case when no users are found
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
		}
	}

	@Override
	public List<User> getAll() {
		return repository.findAll();
	}

}


