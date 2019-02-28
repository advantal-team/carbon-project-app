package com.advantal.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advantal.model.User;
import com.advantal.repositories.UserRepository;
import com.advantal.service.UserService;
import com.advantal.util.DesEncrypter;
import com.advantal.util.IConstant;
import com.advantal.util.RandomStringGenerator;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public User saveuserDetail(User user) {
        // List<User>userList=userRepository.findByMobile(Integer.parseInt(user.getMobileNo()));
		User users=userRepository.findByMobile(user.getMobileNo());
		if (users==null) {
			user.setAddedDate(new Date());
			String otp = RandomStringGenerator.getRandomNumberString(6);
			user.setOtp(otp);
			user.setStatus(IConstant.OTP_SENT);
			user = userRepository.save(user);
		} else {
			if (user.getPassword1() != null && user.getPassword2() != null && !user.getPassword1().equals("")
					&& !user.getPassword2().equals("")) {
				//User users = userRepository.findByMobileNo(user.getMobileNo()).get(0);
				users.setPassword1(DesEncrypter.encrypt(user.getPassword1()));
				users.setPassword2(DesEncrypter.encrypt(user.getPassword2()));
				users.setQuickPassword(DesEncrypter.encrypt(user.getQuickPassword()));
				users.setAddedDate(new Date());
				users.setUserRole(user.getUserRole());
				users = userRepository.save(users);
				user.setStatus(users.getStatus());
			}
		}
		return user;
	}
	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			User user1 = null;
			return user1;
		}
		return user.get();
	}
	@Override
	public Boolean verifyUser(User user) {
		User users = userRepository.findByMobile(user.getMobileNo());
		if (users != null && user != null) {
			if (user.getOtp().equals(users.getOtp())) 
				users.setStatus(IConstant.OTP_VERIFIED);
			    users=userRepository.save(users);
				return true;

			} else {
				return false;
			}
	}
	@SuppressWarnings("unused")
	@Override
	public Boolean resendOtp(User user) {
		User users = userRepository.findByMobile(user.getMobileNo());
		if(users!=null) {
			String otp = RandomStringGenerator.getRandomNumberString(6);
			users.setOtp(otp);
			users=userRepository.save(users);
			return true;
		}
		else {
			return false;
		}
	}
}