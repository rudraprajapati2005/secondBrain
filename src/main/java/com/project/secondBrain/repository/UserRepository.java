package com.project.secondBrain.repository;

import com.project.secondBrain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
