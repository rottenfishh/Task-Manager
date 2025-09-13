package ru.nsu.kolodina.taskmanager;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> getTasksbyUserName(String userName) {
        return taskRepository.findByUserUserName(userName);
    }

    public List<Task> getTasksFiltered(String userName, String category, Task.Status status) {
        if (category != null && status != null) {
            return taskRepository.findByUserUserNameAndCategoryAndStatus(userName, category, status);
        }
        else if (category != null) {
            return taskRepository.findByUserUserNameAndCategory(userName, category);
        } else {
            return taskRepository.findByUserUserName(userName);
        }

    }

    public void createTask(String taskName, String description, User user) {
        Task task = new Task();
        task.setTaskName(taskName);
        task.setTaskDescription(description);
        task.setUser(user);
        task.setStatus(Task.Status.CREATED);
        taskRepository.save(task);
    }

    public void editTaskName(Long id, String newTaskName, User user) {
        if (taskRepository.existsById(id)) {
            Task task = taskRepository.findById(id).get();
            if (task.getUser().getUsername().equals(user.getUsername())) {
                task.setTaskName(newTaskName); taskRepository.save(task);
            }
        }
    }

    public void editTaskDescription(Long id, String newDescription, User user) {
        if (taskRepository.existsById(id)) {
            Task task = taskRepository.findById(id).get();
            if (task.getUser().getUsername().equals(user.getUsername())) {
                task.setTaskDescription(newDescription); taskRepository.save(task);
            }
        }
    }

    public void editTaskStatus(Long id, Task.Status newStatus, User user) {
        if (taskRepository.existsById(id)) {
            Task task = taskRepository.findById(id).get();
            if (task.getUser().getUsername().equals(user.getUsername())) {
                task.setStatus(newStatus); taskRepository.save(task);
            }
        }
    }

    public void editTaskCategory(Long id, String newCategory, User user) {
        if (taskRepository.existsById(id)) {
            Task task = taskRepository.findById(id).get();
            if (task.getUser().getUsername().equals(user.getUsername())) {
                task.setCategory(newCategory); taskRepository.save(task);
            }
        }
    }
    public void deleteTask(Long id,  User user) {
        if (taskRepository.existsById(id)) {
            Task task = taskRepository.findById(id).get();
            if (task.getUser().getUsername().equals(user.getUsername())) {
                taskRepository.delete(task);
            }
        }
    }
}
