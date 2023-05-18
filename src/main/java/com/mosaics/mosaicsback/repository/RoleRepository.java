package com.mosaics.mosaicsback.repository;

import com.mosaics.mosaicsback.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
