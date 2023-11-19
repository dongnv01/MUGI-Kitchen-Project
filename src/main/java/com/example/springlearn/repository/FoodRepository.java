package com.example.springlearn.repository;

import com.example.springlearn.dto.FoodDto;
import com.example.springlearn.entities.Food;
import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    List<Food>findByFoodNameContaining(String keyword);
}
