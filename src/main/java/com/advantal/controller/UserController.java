package com.advantal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

@RestController
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
//===================================================	
	@PostMapping(value="/save_user")
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
        userService.saveuserDetail(user);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
//===================================================		
	@GetMapping(value = "/user_list")
	public ResponseEntity<List<User>> listAllUsers() {
	        List<User> users = userRepository.findAll();
	        if (users.isEmpty()) {
	            return new ResponseEntity(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
//===================================================	
	 @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	    public ResponseEntity<?> getUser(@PathVariable("id") Integer id) {
	        User user = userService.findById(id);
	        if(user == null) {
	        	return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<User>(user, HttpStatus.OK);
	    }
//===================================================		
	 @RequestMapping(value = "/deleteuser/{id}", method = RequestMethod.DELETE)
	  public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Integer userId) throws Exception {
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
}