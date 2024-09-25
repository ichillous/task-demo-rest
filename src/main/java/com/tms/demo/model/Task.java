package com.tms.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // All in one annotation
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
}
