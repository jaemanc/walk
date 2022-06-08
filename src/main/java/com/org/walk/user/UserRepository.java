package com.org.walk.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity getUserByName(String name);

    UserEntity getUserById(Long id);

}
