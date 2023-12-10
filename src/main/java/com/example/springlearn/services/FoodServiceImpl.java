package com.example.springlearn.services;

import com.example.springlearn.dto.FoodDto;
import com.example.springlearn.entities.Food;
import com.example.springlearn.mapper.FoodMapper;
import com.example.springlearn.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService{
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodMapper foodMapper;
    @Override
    public List<FoodDto> getAll() {
        List<FoodDto> foodDTOS = foodRepository.findAll().stream().map(foodMapper::convertToDto).toList(); //or (x->foodMapper.convertToDto(x))

        return foodDTOS;
    }

    @Override
    public Page<FoodDto> listAll(Pageable pageable) {
        Page<FoodDto> foodDtos = foodRepository.findAll(pageable).map(foodMapper::convertToDto);
        return foodDtos;
    }

    @Override
    public boolean save(FoodDto dto) {
        try {
            Food food = new Food();
            foodRepository.save(food.toEntity(dto));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(FoodDto dto) {
        try {
            long id = dto.getId();

            Optional<Food> foodOptional = foodRepository.findById(id);
            if (foodOptional.isPresent()) {
                Food food = foodOptional.get();
                food.setFoodName(dto.getFoodName());
                food.setPrice(dto.getPrice());
                foodRepository.save(food.toEntity(dto));

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            foodRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public FoodDto findById(long id) {
        Optional<FoodDto> list_food = foodRepository.findById(id).stream().map(x->foodMapper.convertToDto(x)).findFirst();
        return list_food.get();
    }

    @Override
    public List<Food> findByName(String name) {

        return foodRepository.findByFoodNameContaining(name);
    }

    public void rateFood(Long foodId, Double rating) {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isPresent()) {
            Food food = optionalFood.get();
            food.setRating(rating);


            foodRepository.save(food);
        }
    }
}
