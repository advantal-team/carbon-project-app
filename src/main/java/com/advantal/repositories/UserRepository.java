package com.advantal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.advantal.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
