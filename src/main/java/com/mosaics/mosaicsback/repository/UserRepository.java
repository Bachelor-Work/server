package com.mosaics.mosaicsback.repository;

import com.mosaics.mosaicsback.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Query("select u from User u where u.email = :email")
    User findByEmail(String email);

}