package com.example.springlearn.controller;

import com.example.springlearn.dto.UserDto;
import com.example.springlearn.services.FoodService;
import com.example.springlearn.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String show_register(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user",userDto);
        return "register";
    }
    @PostMapping("/register/save")
    public String register(@ModelAttribute("user") UserDto userDto){
        userService.saveUser(userDto);
        return "";
    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        return "redirect:/login?logout";
    }


}
