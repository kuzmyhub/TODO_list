package todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todo.model.Item;
import todo.model.User;
import todo.service.TaskService;
import todo.util.SessionUser;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/todo")
    public String getTasks(@RequestParam (name = "done", required = false) String done,
                           @RequestParam (name = "delete", required = false) Boolean delete,
                           Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("delete", delete);
        model.addAttribute("user", user);
        if (done == null) {
            model.addAttribute("tasks", taskService.findAll(user));
        } else if (done.equals("1")) {
            model.addAttribute("tasks", taskService.findByDoneTrue(user));
        } else if (done.equals("0")) {
            model.addAttribute("tasks", taskService.findByDoneFalse(user));
        }
        return "tasks";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("item", new Item());
        model.addAttribute("user", user);
        model.addAttribute("clientId", user.getId());
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
                           @RequestParam(
                                   name = "success", required = false
                           ) boolean success, HttpSession httpSession) {
        Optional<Item> optionalItem = taskService.findById(id);
        if (optionalItem.isEmpty()) {
            return "redirect:/todo";
        }
        Item item = optionalItem.get();
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("task", item);
        model.addAttribute("success", success);
        model.addAttribute("user", user);
        return "task";
    }

    @GetMapping("/changeStatus")
    public String changeStatus(@ModelAttribute("id") int id) {
        Optional<Item> optionalItem = taskService.findById(id);
        if (optionalItem.isEmpty()) {
            return "redirect:/todo";
        }
        Item item = optionalItem.get();
        boolean done = item.isDone();
        if (done) {
            taskService.updateDone(id, false);
        } else {
            taskService.updateDone(id, true);
        }
        return "redirect:/openTask/" + id + "?success=true";
    }

    @GetMapping("/formEditDescription")
    public String editDescription(Model model,
                                  @ModelAttribute("id") int id,
                                  HttpSession httpSession) {
        Optional<Item> optionalItem = taskService.findById(id);
        if (optionalItem.isEmpty()) {
            return "redirect:/todo";
        }
        Item item = optionalItem.get();
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("task", item);
        model.addAttribute("user", user);
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
