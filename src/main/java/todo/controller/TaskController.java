package todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todo.model.Task;
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
    public String getTasks(@RequestParam (name = "done", required = false) Boolean done,
                           @RequestParam (name = "delete", required = false) Boolean delete,
                           Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("delete", delete);
        model.addAttribute("user", user);
        if (done == null) {
            model.addAttribute("tasks", taskService.findAll(user));
        } else if (done.equals(true)) {
            model.addAttribute("tasks", taskService.findByDone(user, true));
        } else if (done.equals(false)) {
            model.addAttribute("tasks", taskService.findByDone(user, false));
        }
        return "task/tasks";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("user", user);
        return "task/addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task,
                             HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        task.setUser(user);
        taskService.add(task);
        return "redirect:/todo";
    }

    @GetMapping("/openTask/{id}")
    public String openTask(Model model,
                           @PathVariable("id") int id,
                           @RequestParam(
                                   name = "success", required = false
                           ) boolean success, HttpSession httpSession) {
        Optional<Task> optionalTask = taskService.findById(id);
        User user = SessionUser.getSession(httpSession);
        if (optionalTask.isEmpty()) {
            model.addAttribute("user", user);
            return "task/404";
        }
        Task task = optionalTask.get();
        model.addAttribute("task", task);
        model.addAttribute("success", success);
        model.addAttribute("user", user);
        return "task/task";
    }

    @GetMapping("/changeStatus")
    public String changeStatus(Model model, @ModelAttribute("id") int id,
                               HttpSession httpSession) {
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty()) {
            User user = SessionUser.getSession(httpSession);
            model.addAttribute("user", user);
            return "task/404";
        }
        Task task = optionalTask.get();
        boolean done = task.isDone();
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
        User user = SessionUser.getSession(httpSession);
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty()) {
            model.addAttribute("user", user);
            return "task/404";
        }
        Task task = optionalTask.get();
        model.addAttribute("task", task);
        model.addAttribute("user", user);
        return "task/editDescription";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("task") Task task) {
        taskService.updateDescription(task.getId(), task.getDescription());
        return "redirect:/openTask/" + task.getId();
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute("id") int id) {
        taskService.delete(id);
        return "redirect:/todo?delete=true";
    }
}
