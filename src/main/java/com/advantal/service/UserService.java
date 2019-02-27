package com.advantal.service;

import com.advantal.model.User;
import com.advantal.util.ResourceNotFoundException;

public interface UserService {
	
	public User saveuserDetail(User user) ;
	public User findById(Integer id) ;

}
