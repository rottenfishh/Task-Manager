package ru.nsu.kolodina.taskmanager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> getTasks(Authentication auth, @RequestParam(required = false) String category, @RequestParam(required = false) Task.Status status) {
        String name = auth.getName();
        return taskService.getTasksFiltered(name, category, status);
    }

    //map users to tasks
    @PostMapping
    public void createTask(@RequestBody TaskRequest task, @AuthenticationPrincipal User auth) {
        taskService.createTask(task.getName(), task.getDescription(), auth);
    }

    @PutMapping("/{idx}")
    public void editTaskName(@PathVariable Long idx, @RequestBody String taskName, @AuthenticationPrincipal User auth) {
        taskService.editTaskName(idx, taskName, auth);
    }

    @PutMapping("/{idx}/description")
    public void editTaskDescription(@PathVariable Long idx, @RequestBody String description, @AuthenticationPrincipal User auth) {
        taskService.editTaskDescription(idx, description, auth);
    }

    @PutMapping("{idx}/category")
    public void addCategory(@PathVariable Long idx, @RequestBody String category, @AuthenticationPrincipal User auth) {
        taskService.editTaskCategory(idx, category, auth);
    }
    @PutMapping("/{idx}/status")
    public void editTaskStatus(@PathVariable Long idx, @RequestBody Task.Status status, @AuthenticationPrincipal User auth) {
        taskService.editTaskStatus(idx, status, auth);
    }
    @DeleteMapping("/{idx}")
    public void deleteTask(@PathVariable Long idx, @AuthenticationPrincipal User auth) {
        taskService.deleteTask(idx, auth);
    }
}
