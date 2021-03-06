package com.spring.app.service;

import com.spring.app.entity.User;

public interface UserService {

	public Iterable<User> getAllUsers();
	
	public User createUser(User user) throws Exception;
	
	public User updateUser(User user) throws Exception;
	
	public User updateUserSinId(User user) throws Exception;

	public void deleteUser(Long id) throws Exception;
	
	public User getUserById(Long id) throws Exception;
	
	public User getUserByEmail(String id) throws Exception;
}
