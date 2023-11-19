package com.example.springlearn.controller;

import com.example.springlearn.dto.FoodDto;
import com.example.springlearn.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    @Autowired
    private FoodService foodService;
    @GetMapping("/create_oder")
    public String createOder(){
        return "create_oder";
    }


}
