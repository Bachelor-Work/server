package com.mosaics.mosaicsback.repository;

import com.mosaics.mosaicsback.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}