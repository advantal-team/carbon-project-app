package com.advantal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.advantal.model.User;
import com.advantal.repositories.UserRepository;
import com.advantal.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	@PostMapping(value="/save_user")
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
        userService.saveuserDetail(user);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
	
	 @GetMapping(value = "/user_list")
	    public ResponseEntity<List<User>> listAllUsers() {
	        List<User> users = userRepository.findAll();
	        if (users.isEmpty()) {
	            return new ResponseEntity(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	    }


}
