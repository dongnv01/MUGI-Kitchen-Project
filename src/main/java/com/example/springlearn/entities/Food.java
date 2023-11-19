package com.example.springlearn.entities;

import com.example.springlearn.dto.FoodDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food")
@Builder
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foodName;
    private String image;
    private Integer price;

    public Food toEntity(FoodDto dto){
        return Food.builder().id(dto.getId()).foodName(dto.getFoodName()).price(dto.getPrice()).image(dto.getImage())
                .build();
    }
}
