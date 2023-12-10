package com.example.springlearn.controller;

import com.example.springlearn.dto.FoodDto;
import com.example.springlearn.dto.ReservationDto;
import com.example.springlearn.dto.UserDto;
import com.example.springlearn.entities.Food;
import com.example.springlearn.entities.Reservation;
import com.example.springlearn.repository.ReservationRepository;
import com.example.springlearn.services.FoodService;
import com.example.springlearn.services.FoodServiceImpl;
import com.example.springlearn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/list-food")
    public ResponseEntity<Page<FoodDto>> getFoods(@RequestParam(name = "page") Optional<Integer> page,
                                                  @RequestParam(name = "size") Optional<Integer> size,
                                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        int pageNumber = page.orElse(1);
        int pageSize = size.orElse(6);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id").descending());
        Page<FoodDto> listDto = foodService.listAll(pageable);
        return ResponseEntity.ok(listDto);
    }

    @PostMapping("/reserve-table")
    @ResponseBody
    public ResponseEntity<String> reserveTable(@RequestBody ReservationDto reservationDto) {
            Reservation reservation = new Reservation();
            reservation.setName(reservationDto.getName());
            reservation.setEmail(reservationDto.getEmail());
            reservation.setPhone(reservationDto.getPhone());
            reservation.setPeople(reservationDto.getPeople());
            reservation.setDate(reservationDto.getDate());
            reservation.setTime(reservationDto.getTime());
            reservation.setMessage(reservationDto.getMessage());

            // Lưu thông tin đặt bàn vào cơ sở dữ liệu
            reservationRepository.save(reservation);


            return ResponseEntity.ok("Đặt bàn thành công!");

    }
    @GetMapping("/all-food")
    public ResponseEntity<List<FoodDto>> getAllFoods(){
        List<FoodDto> foodList = foodService.getAll();
        return ResponseEntity.ok(foodList);
    }

    @PostMapping("/{foodId}/rate")
    public ResponseEntity<String> rateFood(@PathVariable Long foodId, @RequestParam Double rating) {
        // Gọi service để lưu rating vào database
        foodService.rateFood(foodId, rating);
        return ResponseEntity.ok("Rating successful");
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new UserDto());
        return "login";
    }
}
