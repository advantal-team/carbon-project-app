package com.advantal.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.advantal.model.User;
import java.lang.Long;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByMobileNo(String mobileNo);
	
}
