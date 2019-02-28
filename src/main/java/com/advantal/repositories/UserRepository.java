package com.advantal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.advantal.model.User;

//public interface UserRepository extends JpaRepository<User, Long>{
//	User findByMobileNo(String mobileNo);
//}

public interface UserRepository extends JpaRepository<User, Long>{
	@Query(value = "select * from USER u where u.mobile_no=?1", nativeQuery = true)
    User findByMobile(String mobileNo);
}
