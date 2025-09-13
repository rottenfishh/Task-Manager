package ru.nsu.kolodina.taskmanager;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findByUserId(Long userId);
    public List<Task> findByUserUserName(String userName);
    public List<Task> findByUserUserNameAndCategory(String userName, String category);
    public List<Task> findByUserUserNameAndStatus(String userName, Task.Status status);
    public List<Task> findByUserUserNameAndCategoryAndStatus(String userName, String category, Task.Status status);
}
