package com.org.walk.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity getUserByName(String name);

    @EntityGraph(attributePaths = {"file"})
    UserEntity getUserByUserId(Long userId);

    UserEntity getUserByEmail(String email);

}
