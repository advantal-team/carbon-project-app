package com.advantal.service;

import com.advantal.model.User;

public interface UserService {
	public User saveuserDetail(User user) ;
	public User findById(Long id);
	//public List<User> findByUserId(Long id);
	public Boolean verifyUser(User user);
	public Boolean resendOtp(User user);
}
