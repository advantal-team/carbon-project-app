package com.advantal.service;

import java.util.List;

import com.advantal.model.User;
import com.advantal.util.ResourceNotFoundException;

public interface UserService {
	
	public User saveuserDetail(User user) ;
	public User findById(Long id);
	//public List<User> findByUserId(Long id);
	public Boolean verifyUser(User user);


}
