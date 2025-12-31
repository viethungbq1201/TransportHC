package com.example.TransportHC.repository;

import com.example.TransportHC.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


}
