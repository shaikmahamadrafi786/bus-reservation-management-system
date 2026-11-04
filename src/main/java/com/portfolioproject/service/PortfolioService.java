package com.portfolioproject.service;
import com.portfolioproject.exception.InvalidStockException;


import com.portfolioproject.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.portfolioproject.exception.UserNotFoundException;
public class PortfolioService {

    private Map<String, User> users = new HashMap<>();


    // Add User
    public void addUser(User user) {

        users.put(user.getUserid(), user);
    }


    // Check whether user exists
    public boolean userExists(String userid) {

        return users.containsKey(userid);
    }


    // Get User by ID
//    public User getUser(String userid) {
//
//        return users.get(userid);
//    }
    public User getUser(String userid) throws UserNotFoundException 
    {
        User user = users.get(userid);
        if (user == null)
        {
            throw new UserNotFoundException("User not found: " + userid);
        }
        return user;    
     }

    // Get all users
    public Collection<User> getAllUsers() 
    {
        return users.values();
    }
    public void loadUsers(Collection<User> loadedUsers) 
    {
        for (User user : loadedUsers) 
        {
            users.put(user.getUserid(), user);
        }
    }
    public void validateStock(int quantity, double price)
            throws InvalidStockException {

        if (quantity <= 0) {
            throw new InvalidStockException(
                "Stock quantity must be greater than zero."
            );
        }

        if (price <= 0) {
            throw new InvalidStockException(
                "Stock price must be greater than zero."
            );
        }
    }
}