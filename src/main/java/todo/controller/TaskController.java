package todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todo.model.Item;
import todo.service.TaskService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/todo")
    public String getTasks(@RequestParam (name = "done", required = false) String done, Model model) {
        if (done == null) {
            model.addAttribute("tasks", taskService.findAll());
        } else if (done.equals("1")) {
            model.addAttribute("tasks", taskService.findByDoneTrue());
        } else if (done.equals("0")) {
            model.addAttribute("tasks", taskService.findByDoneFalse());
        }
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

    @GetMapping("/openTask/{id}")
    public String openTask(Model model,
                           @PathVariable("id") int id) {
        Item item = taskService.findById(id);
        model.addAttribute("task", item);
        return "task";
    }

    @GetMapping("/changeStatus")
    public String changeStatus(@ModelAttribute("id") int id) {
        Item item = taskService.findById(id);
        boolean done = item.isDone();
        if (done) {
            taskService.update(id, false);
        } else {
            taskService.update(id, true);
        }
        return "redirect:/openTask/" + id;
    }
}
