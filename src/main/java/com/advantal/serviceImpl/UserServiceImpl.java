package com.advantal.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advantal.model.User;
import com.advantal.repositories.UserRepository;
import com.advantal.service.UserService;
import com.advantal.util.DesEncrypter;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	

	@Override
	public User saveuserDetail(User user) {
		
		if(user.getUserId()==null) {
			user.setAddedDate(new Date());
			user.setPassword1(DesEncrypter.encrypt(user.getPassword1()));
			user.setPassword2(DesEncrypter.encrypt(user.getPassword2()));
			user=userRepository.save(user);
		}
		else {
			user.setUpdateDate(new Date());
			user=userRepository.save(user);
		}
		
		return user;
	}

}
