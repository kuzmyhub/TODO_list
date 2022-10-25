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
    public String getTasks(@RequestParam (name = "done", required = false) String done,
                           @RequestParam (name = "delete", required = false) Boolean delete,
                           Model model) {
        model.addAttribute("delete", delete);
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
                           @PathVariable("id") int id,
                           @RequestParam(name = "success", required = false) boolean success) {
        Item item = taskService.findById(id);
        model.addAttribute("task", item);
        model.addAttribute("success", success);
        return "task";
    }

    @GetMapping("/changeStatus")
    public String changeStatus(@ModelAttribute("id") int id) {
        Item item = taskService.findById(id);
        boolean done = item.isDone();
        if (done) {
            taskService.updateDone(id, false);
        } else {
            taskService.updateDone(id, true);
        }
        return "redirect:/openTask/" + id + "?success=true";
    }

    @GetMapping("/formEditDescription")
    public String changeDescription(Model model, @ModelAttribute("id") int id) {
        Item item = taskService.findById(id);
        model.addAttribute("task", item);
        return "editDescription";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("task") Item item) {
        System.out.println(item + "item");
        taskService.updateDescription(item.getId(), item.getDescription());
        return "redirect:/openTask/" + item.getId();
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute("id") int id) {
        taskService.delete(id);
        return "redirect:/todo?delete=true";
    }
}
