package com.example.springlearn.controller;

import com.example.springlearn.dto.FoodDto;
import com.example.springlearn.dto.ReservationDto;
import com.example.springlearn.dto.UserDto;
import com.example.springlearn.entities.Food;
import com.example.springlearn.entities.Reservation;
import com.example.springlearn.repository.ReservationRepository;
import com.example.springlearn.services.FoodService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.domain.Pageable;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@MultipartConfig
public class CheckoutController {
    @Value("${upload.path}")
    private String fileUpload = "image";
    @Autowired
    private FoodService foodService;
    @Autowired
    private ReservationRepository reservationRepository;
    @GetMapping("/admin")
    public String pageIndex(Model model, @RequestParam(name = "page")Optional<Integer> page,
                            @RequestParam(name = "size") Optional<Integer> size,
                            @RequestParam(name = "sort", required = false,defaultValue = "ASC") String sort){

        int pageindex = page.orElse(1);
        int pagesize = size.orElse(4);
        Pageable pageable = (Pageable) PageRequest.of(pageindex-1,pagesize, Sort.by("id").descending());
        Page<FoodDto> list_dto = foodService.listAll(pageable);
        int totalPages = list_dto.getTotalPages();
        long totalItems = list_dto.getTotalElements();

        model.addAttribute("list_food",list_dto);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("curentPage",pageindex);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "admin";
    }
    @GetMapping(value = "/add", name = "/add")
    public String add(Model model){

        model.addAttribute("food",new FoodDto());
        return "add";
    }

    @PostMapping(value = "/add", name = "/add")
    public String do_add(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("food")FoodDto dto){
        try {
            MultipartFile multipartFile = dto.getFile();
            String filename = multipartFile.getOriginalFilename();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                    new File(fileUpload + File.separator + filename)));
            stream.write(multipartFile.getBytes());
            stream.close();
            dto.setImage(filename);
            foodService.save(dto);
            redirectAttributes.addAttribute("messagesuccess","oki");
        }catch (Exception e){

        }
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit/{id}", name = "/edit")
    public String edit(Model model, @PathVariable("id")Long id){

        FoodDto dto = foodService.findById(id);
        model.addAttribute("fooddto",dto);
        return "edit";
    }

    @PostMapping(value = "/edit", name = "/edit")
    public String edit(HttpServletRequest httpServlet, Model model, FoodDto dto){
        try {
            MultipartFile multipartFile = dto.getFile();
            String filename = multipartFile.getOriginalFilename();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                    new File(fileUpload + File.separator + filename)));
            stream.write(multipartFile.getBytes());
            stream.close();
            dto.setImage(filename);
            foodService.save(dto);
        }catch (Exception e){

        }

        return "redirect:/admin";
    }

    @GetMapping(value = "/delete/{id}", name = "/delete")
    public String delete(@PathVariable("id") Long id) {
        foodService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "search-page";
    }

    @PostMapping("/search")
    public String searchByName(@RequestParam("search") String name, Model model) {
        List<Food> foundFoods = foodService.findByName(name);
        model.addAttribute("foundFoods", foundFoods);
        return "search-page";
    }

    @GetMapping("/reservation-list")
    public String viewReservations(Model model) {
        List<Reservation> reservations = reservationRepository.findAll();
        model.addAttribute("reservations", reservations);
        return "reservation-list";
    }

    @GetMapping(value = "/delete-reservation/{id}", name = "/delete-reservation")
    public String deleteReservtion(@PathVariable Long id){
        reservationRepository.deleteById(id);
        return "redirect:/reservation-list";
    }
}
