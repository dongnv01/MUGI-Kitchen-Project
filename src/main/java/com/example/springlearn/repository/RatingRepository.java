package com.example.springlearn.repository;

import com.example.springlearn.entities.Food;
import com.example.springlearn.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByFood(Food food);
}
