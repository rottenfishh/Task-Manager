package ru.nsu.kolodina.taskmanager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @GetMapping("/tasks")
    public String returnTasksPage() {
        return "index";
    }
}
