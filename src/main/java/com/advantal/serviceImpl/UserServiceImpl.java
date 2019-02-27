package com.advantal.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advantal.model.User;
import com.advantal.repositories.UserRepository;
import com.advantal.service.UserService;
import com.advantal.util.DesEncrypter;
import com.advantal.util.RandomStringGenerator;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;	
	@Override
	public User saveuserDetail(User user) {
		if(user.getUserId()==null) {
			user.setAddedDate(new Date());
			String otp = RandomStringGenerator.getRandomNumberString(6);
			user.setOtp(otp);
			user.setStatus(1);
			user=userRepository.save(user);
		}
		else {
			if(user.getPassword1()!=null && user.getPassword2()!=null && !user.getPassword1().equals("") && !user.getPassword2().equals("")) {
				user.setPassword1(DesEncrypter.encrypt(user.getPassword1()));
				user.setPassword2(DesEncrypter.encrypt(user.getPassword2()));
				user.setStatus(2);
			}
		}
		return user;
	}
	
	@Override
    public User findById(Integer id) {
	  Optional<User> user = userRepository.findById(id);
      if (!user.isPresent())
      { 	
    	 User user1 = null;
         return user1;
      }
	  return user.get();
    }
}


