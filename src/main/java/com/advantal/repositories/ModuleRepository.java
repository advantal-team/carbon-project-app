package com.advantal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advantal.model.Module;

public interface ModuleRepository  extends JpaRepository<Module, Integer>{

}
