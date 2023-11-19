package com.example.springlearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDto {
    private long id;
    private String foodName;
    private Integer price;
    private MultipartFile file;
    private String image;
}
