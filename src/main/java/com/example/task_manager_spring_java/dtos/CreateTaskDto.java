package com.example.task_manager_spring_java.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTaskDto {        // Data Transfer Object (DTO)
    String title;
    String description;
    String deadline;
}
