package com.apap.tutorial08.repository;

import com.apap.tutorial08.model.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDB extends JpaRepository<UserRoleModel,Long> {
    UserRoleModel findByusername(String username);

}
