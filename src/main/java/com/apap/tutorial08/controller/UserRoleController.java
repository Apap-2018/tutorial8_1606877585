package com.apap.tutorial08.controller;

import com.apap.tutorial08.model.UserRoleModel;
import com.apap.tutorial08.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;



    @RequestMapping(value = "/addUser" , method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserRoleModel userRoleModel,Model model){
        try {
            userRoleService.addUser(userRoleModel);
        }catch (Exception e){
                model.addAttribute("Masalah","Password minimal terdiri dari 8 suku kata dan mengandung huruf-angka");
                return "password-bermasalah";
        }

        return "home";
    }

    @RequestMapping(value = "/updatePass",method = RequestMethod.POST)
    private String updatePass(HttpServletRequest request, Model model){
        String oldPass = request.getParameter("passwordLama");
        String newPass =  request.getParameter("password");
        String matchPass = request.getParameter("passwordMatch");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserRoleModel userRoleModel = userRoleService.findByUsername(auth.getName());

        if (!userRoleService.checkCurrentPassword(userRoleModel,oldPass)){
            model.addAttribute("Masalah","Password lama salah");
            return "password-bermasalah";
        }if (!userRoleService.checkMatchPas(newPass,matchPass)){
            model.addAttribute("Masalah","Password baru yang diinputkan tidak sama");
            return "password-bermasalah";
        }

        userRoleModel.setPassword(newPass);
        try {
            userRoleService.addUser(userRoleModel);
        }catch (Exception e){
            model.addAttribute("Masalah","Password minimal terdiri dari 8 suku kata dan mengandung huruf-angka");
            return "password-bermasalah";
        }

        return "home";
    }
}
