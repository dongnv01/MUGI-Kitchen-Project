package com.example.springlearn.mapper;

import com.example.springlearn.dto.FoodDto;
import com.example.springlearn.entities.Food;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class FoodMapper {
    public FoodDto convertToDto(Food entity){
        return FoodDto.builder().id(entity.getId()).foodName(entity.getFoodName()).price(entity.getPrice()).image(entity.getImage()).rating(entity.getRating())
                .build();
    }


}
