package com.apap.tutorial08.service;

import com.apap.tutorial08.model.UserRoleModel;
import com.apap.tutorial08.repository.UserRoleDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDB userRoleDB;

    @Override
    public UserRoleModel addUser(UserRoleModel user)throws Exception {
        if (!checkCondition(user.getPassword()))
            throw new Exception();
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userRoleDB.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedpassword = passwordEncoder.encode(password);
        return hashedpassword;
    }

    @Override
    public UserRoleModel findByUsername(String username) {
        return userRoleDB.findByusername(username);
    }

    @Override
    public boolean checkCurrentPassword(UserRoleModel userRoleModel ,String oldPass){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(oldPass,userRoleModel.getPassword());
    }
    @Override
    public boolean checkMatchPas(String pasword , String matchpassword){
        return pasword.equals(matchpassword);
    }
    @Override
    public boolean checkCondition(String password){
        boolean passwordContainsDigit = false;
        boolean passwordContainsLetter = false;

        if (!(password.length() >= 8)){
            return false;
        }

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                passwordContainsDigit = true;
            }
        }
        if (!passwordContainsDigit)
            return false;

        passwordContainsLetter = password.matches(".*[a-zA-Z]+.*");

        return passwordContainsLetter;
    }
}
