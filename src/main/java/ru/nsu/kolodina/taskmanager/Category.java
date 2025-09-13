package ru.nsu.kolodina.taskmanager;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    String categoryName;

    @OneToMany
    @JoinColumn(name = "TaskId")
    public List<Task> tasks;
}
