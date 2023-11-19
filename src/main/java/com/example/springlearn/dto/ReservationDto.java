package com.example.springlearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private String name;
    private String email;
    private String phone;
    private int people;
    private LocalDate date;
    private String time;
    private String message;
}
