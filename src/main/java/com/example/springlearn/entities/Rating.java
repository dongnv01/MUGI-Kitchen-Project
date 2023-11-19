package com.example.springlearn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value; // Giá trị đánh giá, ví dụ: 4.3

    @ManyToOne
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
