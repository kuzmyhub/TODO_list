package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.*;
import ru.job4j.todo.util.SessionUser;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService hibernateTaskService;
    private final PriorityService hibernatePriorityService;
    private final CategoryService hibernateCategoryService;

    @GetMapping("/todo")
    public String getTasks(@RequestParam (name = "done", required = false) Boolean done,
                           @RequestParam (name = "delete", required = false) Boolean delete,
                           Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("delete", delete);
        model.addAttribute("user", user);
        if (done == null) {
            model.addAttribute("tasks", hibernateTaskService.findAll(user));
        } else if (done.equals(true)) {
            model.addAttribute("tasks", hibernateTaskService.findByDone(user, true));
        } else if (done.equals(false)) {
            model.addAttribute("tasks", hibernateTaskService.findByDone(user, false));
        }
        return "task/tasks";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("categories", hibernateCategoryService.findAll());
        model.addAttribute("priorities", hibernatePriorityService.findAll());
        return "task/addTask";
    }

    @PostMapping("/createTask")
    public String createTask(Model model,
                             @ModelAttribute Task task,
                             @ModelAttribute(name = "priorityId") int priorityId,
                             @ModelAttribute(name = "categoriesId") List<Integer> categoriesId,
                             HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        Optional<Priority> optionalPriority = hibernatePriorityService.findById(priorityId);
        if (optionalPriority.isEmpty()) {
            model.addAttribute("user", user);
            return "task/404";
        }
        List<Category> categories = hibernateCategoryService
                .findByIds(categoriesId);
        task.setCreated(LocalDateTime.now().atZone(ZoneId.of(user.getUtc())));
        task.setCategories(categories);
        task.setUser(user);
        task.setPriority(optionalPriority.get());
        hibernateTaskService.add(task);
        return "redirect:/todo";
    }

    @GetMapping("/openTask/{id}")
    public String openTask(Model model,
                           @PathVariable("id") int id,
                           @RequestParam(
                                   name = "success", required = false
                           ) boolean success, HttpSession httpSession) {
        Optional<Task> optionalTask = hibernateTaskService.findById(id);
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
        Optional<Task> optionalTask = hibernateTaskService.findById(id);
        if (optionalTask.isEmpty()) {
            User user = SessionUser.getSession(httpSession);
            model.addAttribute("user", user);
            return "task/404";
        }
        Task task = optionalTask.get();
        boolean done = task.isDone();
        if (done) {
            hibernateTaskService.updateDone(id, false);
        } else {
            hibernateTaskService.updateDone(id, true);
        }
        return "redirect:/openTask/" + id + "?success=true";
    }

    @GetMapping("/formEditDescription")
    public String editDescription(Model model,
                                  @ModelAttribute("id") int id,
                                  HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        Optional<Task> optionalTask = hibernateTaskService.findById(id);
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
        hibernateTaskService.updateDescription(task.getId(), task.getDescription());
        return "redirect:/openTask/" + task.getId();
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute("id") int id) {
        hibernateTaskService.delete(id);
        return "redirect:/todo?delete=true";
    }
}
