package todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import todo.model.Item;
import todo.service.TaskService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/todo")
    public String getTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model) {
        model.addAttribute("item", new Item());
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Item item) {
        taskService.add(item);
        return "redirect:/todo";
    }
}
