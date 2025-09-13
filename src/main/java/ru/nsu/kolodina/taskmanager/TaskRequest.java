package ru.nsu.kolodina.taskmanager;

import lombok.Data;

@Data
public class TaskRequest {
    String name;
    String description;
}
