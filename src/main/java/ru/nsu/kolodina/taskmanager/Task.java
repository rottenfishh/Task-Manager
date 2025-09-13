package ru.nsu.kolodina.taskmanager;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String taskName;

    @Column()
    private String taskDescription;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column()
    private String category;

    @Column()
    private Date deadline;

    public enum Status {
        CREATED,
        FINISHED,
    }
}