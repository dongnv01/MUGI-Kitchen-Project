package com.example.springlearn.services;

import com.example.springlearn.dto.FoodDto;
import com.example.springlearn.entities.Food;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FoodService {
    List<FoodDto>getAll();
    Page<FoodDto> listAll(Pageable pageable);
    boolean save(FoodDto dto);
    boolean update(FoodDto dto);
    boolean delete(long id);
    FoodDto findById(long id);
    List<Food>findByName(String name);
}
