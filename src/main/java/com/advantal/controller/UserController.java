package com.advantal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.advantal.model.User;
import com.advantal.repositories.UserRepository;
import com.advantal.service.UserService;
import com.advantal.util.CustomErrorType;
import com.advantal.util.IConstant;

@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	/**
	 * @param user
	 * @param ucBuilder
	 * @return
	 */
	@PostMapping(value="/save_user")
	public Map<Object, Object> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
        user= userService.saveuserDetail(user);
        Map<Object, Object> map = new HashMap();
        if(user!=null && user.getStatus()!=null && user.getStatus()==1) {
        	map.put(IConstant.RESPONSE,  HttpStatus.OK);
			map.put(IConstant.MESSAGE, IConstant.VERIFICATION_CODE_SENT);
        }
        else if(user!=null && user.getStatus()!=null && user.getStatus()==2){
        	map.put(IConstant.RESPONSE,  HttpStatus.CREATED);
			map.put(IConstant.MESSAGE, IConstant.REGISTRATION_SUCCESS_MESSAGE);
        }
        return map;
    }
	/**
	 * @return
	 */
	@GetMapping(value = "/user_list")
	public ResponseEntity<List<User>> listAllUsers() {
	        List<User> users = userRepository.findAll();
	        if (users.isEmpty()) {
	            return new ResponseEntity(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	 /**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
	        User user = userService.findById(id);
	        if(user == null) {
	        	return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<User>(user, HttpStatus.OK);
	    }		
	 /**
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteuser/{id}", method = RequestMethod.DELETE)
	  public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
		 User user =  userService.findById(userId);
		 Map<String, Boolean> response = new HashMap<>();
        if(user == null) 
        {
        	response.put("deleted",  Boolean.FALSE);
        	return response;
        }
	    userRepository.delete(user);
	    response.put("deleted", Boolean.TRUE);
	    return response;
	  }
	/**
	 * @param user
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value="/verify_otp", method = RequestMethod.PUT)
    public  Map<Object, Object> verifyUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
		boolean status=userService.verifyUser(user);
		Map<Object, Object> map = new HashMap();
		if(status) {
			map.put(IConstant.RESPONSE,  HttpStatus.OK);
			map.put(IConstant.MESSAGE, IConstant.VERIFICATION_SUCCESS_MESSAGE);
		}
		else {
			map.put(IConstant.RESPONSE,  HttpStatus.NOT_FOUND);
			map.put(IConstant.MESSAGE, IConstant.VERIFICATION_ERROR_MESSAGE);
		}
		return map;
        
    }
	/**
	 * @param user
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value="/resend_otp", method = RequestMethod.POST)
    public  Map<Object, Object> resendOtp(@RequestBody User user, UriComponentsBuilder ucBuilder){
		boolean status=userService.resendOtp(user);
		Map<Object, Object> map = new HashMap();
		if(status) {
			map.put(IConstant.RESPONSE,  HttpStatus.OK);
			map.put(IConstant.MESSAGE, IConstant.VERIFICATION_RE_SENT);
		}
		else {
			map.put(IConstant.RESPONSE,  HttpStatus.NOT_FOUND);
			map.put(IConstant.MESSAGE, IConstant.INVALID_MOBILE_ERROR_MESSAGE);
		}
		return map;
    }
	/**
	 * @param user
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value="/validate_mobile", method = RequestMethod.POST)
    public  Map<Object, Object> mobileNoExistOrNot(@RequestBody User user, UriComponentsBuilder ucBuilder){
		boolean status=userService.resendOtp(user);
		Map<Object, Object> map = new HashMap();
		if(status) {
			map.put(IConstant.RESPONSE,  IConstant.ALREADY_REPORTED);
			map.put(IConstant.MESSAGE, user.getMobileNo()+":"+IConstant.MOBILE_NO_ALREADY_REGISTER);
		}
		else {
			map.put(IConstant.RESPONSE,  HttpStatus.ACCEPTED);
			map.put(IConstant.MESSAGE, IConstant.INVALID_MOBILE_ERROR_MESSAGE);
		}
		return map;
    }
}