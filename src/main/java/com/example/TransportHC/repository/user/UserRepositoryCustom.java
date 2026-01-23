package com.example.TransportHC.repository.user;

import java.util.List;

import com.example.TransportHC.entity.User;

public interface UserRepositoryCustom {

    List<User> findAllNonAdminUsers();
}
