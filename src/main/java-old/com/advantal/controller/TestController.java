package com.advantal.controller;
import java.util.List;

import com.advantal.model.*;
import com.advantal.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.advantal.repositories.UserRepositoryOld;

@RestController

public class TestController {

	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String msg() {
		return "hello2";
	}

	  @Autowired
	  private UserRepositoryOld userRepository;

	  /**
	   * Get all users list.
	   *
	   * @return the list
	   */
	  @RequestMapping(value="/users",method=RequestMethod.GET)
	  public List<User> getAllUsers() {
	    return  userRepository.findAll();
	  }
	  
	  @RequestMapping("/users/{id}")
	  public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId)
	      throws ResourceNotFoundException {
	    User user =
	        userRepository
	            .findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
	    return ResponseEntity.ok().body(user);
	  }

	

}
