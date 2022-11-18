package ru.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.todo.model.Category;
import ru.todo.model.Priority;
import ru.todo.model.Task;
import ru.todo.model.User;
import ru.job4j.todo.service.*;
import ru.todo.service.CategoryService;
import ru.todo.service.PriorityService;
import ru.todo.service.TaskService;
import ru.todo.util.SessionUser;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService simpleTaskService;
    private final PriorityService simplePriorityService;
    private final CategoryService simpleCategoryService;

    @GetMapping("/todo")
    public String getTasks(@RequestParam (name = "done", required = false) Boolean done,
                           @RequestParam (name = "delete", required = false) Boolean delete,
                           Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("delete", delete);
        model.addAttribute("user", user);
        if (done == null) {
            model.addAttribute("tasks", simpleTaskService.findAll(user));
        } else if (done.equals(true)) {
            model.addAttribute("tasks", simpleTaskService.findByDone(user, true));
        } else if (done.equals(false)) {
            model.addAttribute("tasks", simpleTaskService.findByDone(user, false));
        }
        return "task/tasks";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("categories", simpleCategoryService.findAll());
        model.addAttribute("priorities", simplePriorityService.findAll());
        return "task/addTask";
    }

    @PostMapping("/createTask")
    public String createTask(Model model,
                             @ModelAttribute Task task,
                             @ModelAttribute(name = "priorityId") int priorityId,
                             @ModelAttribute(name = "categoriesId") List<Integer> categoriesId,
                             HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        Optional<Priority> optionalPriority = simplePriorityService.findById(priorityId);
        if (optionalPriority.isEmpty()) {
            model.addAttribute("user", user);
            return "task/404";
        }
        List<Category> categories = simpleCategoryService
                .findByIds(categoriesId);
        task.setCreated(LocalDateTime.now().atZone(ZoneId.of(user.getUtc())));
        task.setCategories(categories);
        task.setUser(user);
        task.setPriority(optionalPriority.get());
        simpleTaskService.add(task);
        return "redirect:/todo";
    }

    @GetMapping("/openTask/{id}")
    public String openTask(Model model,
                           @PathVariable("id") int id,
                           @RequestParam(
                                   name = "success", required = false
                           ) boolean success, HttpSession httpSession) {
        Optional<Task> optionalTask = simpleTaskService.findById(id);
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
        Optional<Task> optionalTask = simpleTaskService.findById(id);
        if (optionalTask.isEmpty()) {
            User user = SessionUser.getSession(httpSession);
            model.addAttribute("user", user);
            return "task/404";
        }
        Task task = optionalTask.get();
        boolean done = task.isDone();
        if (done) {
            simpleTaskService.updateDone(id, false);
        } else {
            simpleTaskService.updateDone(id, true);
        }
        return "redirect:/openTask/" + id + "?success=true";
    }

    @GetMapping("/formEditDescription")
    public String editDescription(Model model,
                                  @ModelAttribute("id") int id,
                                  HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        Optional<Task> optionalTask = simpleTaskService.findById(id);
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
        simpleTaskService.updateDescription(task.getId(), task.getDescription());
        return "redirect:/openTask/" + task.getId();
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute("id") int id) {
        simpleTaskService.delete(id);
        return "redirect:/todo?delete=true";
    }
}
