package com.example.springlearn.controller;

import com.example.springlearn.entities.Food;
import com.example.springlearn.entities.Rating;
import com.example.springlearn.entities.User;
import com.example.springlearn.repository.FoodRepository;
import com.example.springlearn.repository.RatingRepository;
import com.example.springlearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/rate")
    public ResponseEntity<String> rateFood(@RequestParam Long foodId, @RequestParam Long userId , @RequestParam double ratingValue) {
        // Lấy thông tin Food từ cơ sở dữ liệu
        Food food = foodRepository.findById(foodId).orElse(null);

        if (food == null) {
            return ResponseEntity.badRequest().body("Food not found");
        }

        // Lấy thông tin User từ cơ sở dữ liệu (Bạn cần thêm UserRepository)
        User user = userRepository.findById(userId).orElse(null);

        // Tạo mới đánh giá
        Rating rating = new Rating();
        rating.setFood(food);
        rating.setUser(user);
        rating.setValue(ratingValue);

        // Lưu đánh giá vào cơ sở dữ liệu
        ratingRepository.save(rating);

        return ResponseEntity.ok("Food rated successfully");
    }

    @GetMapping("/average/{foodId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long foodId) {
        Food food = foodRepository.findById(foodId).orElse(null);

        if (food == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Rating> ratings = ratingRepository.findByFood(food);

        if (ratings.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }

        double averageRating = ratings.stream().mapToDouble(Rating::getValue).average().orElse(0.0);
        return ResponseEntity.ok(averageRating);
    }
}
