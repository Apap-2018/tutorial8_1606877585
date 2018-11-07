package com.apap.tutorial08.service;

import com.apap.tutorial08.model.UserRoleModel;
import org.springframework.security.core.userdetails.User;

public interface UserRoleService {
    UserRoleModel addUser(UserRoleModel user) throws Exception;
    public String encrypt(String password);
    UserRoleModel findByUsername(String username);
    public boolean checkCurrentPassword(UserRoleModel userRoleModel ,String oldPass);
    public boolean checkMatchPas(String pasword , String matchpassword);
    public boolean checkCondition(String password);
}
