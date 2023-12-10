package com.example.springlearn.entities;

import com.example.springlearn.dto.FoodDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private Double rating;

    public Food toEntity(FoodDto dto){
        return Food.builder().id(dto.getId()).foodName(dto.getFoodName()).price(dto.getPrice()).image(dto.getImage()).rating(dto.getRating())
                .build();
    }

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}
