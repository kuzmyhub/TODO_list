package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.HibernateCategoryService;
import ru.job4j.todo.service.HibernatePriorityService;
import ru.job4j.todo.service.HibernateTaskService;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Test
    public void whenGetTasksThanGetAllTasks() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        Boolean done = null;
        boolean delete = true;
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        String expected = "task/tasks";
        String page = taskController.getTasks(done, delete, model, httpSession);
        verify(model).addAttribute("delete", delete);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenGetTasksThanGetTrueTasks() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        boolean done = true;
        boolean delete = true;
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        String expected = "task/tasks";
        String page = taskController.getTasks(done, delete, model, httpSession);
        verify(model).addAttribute("delete", delete);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenGetTasksThanGetFalseTasks() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        boolean done = true;
        boolean delete = false;
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        String expected = "task/tasks";
        String page = taskController.getTasks(done, delete, model, httpSession);
        verify(model).addAttribute("delete", delete);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenAddTask() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        Category category = new Category();
        category.setName("java");
        Priority priority = new Priority();
        priority.setName("not urgent");
        priority.setPosition(3);
        List<Category> categories = List.of(category);
        List<Priority> priorities = List.of(priority);
        when(hibernateCategoryService.findAll()).thenReturn(categories);
        when(hibernatePriorityService.findAll()).thenReturn(priorities);
        String expected = "task/addTask";
        String page = taskController.addTask(model, httpSession);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("categories", categories);
        verify(model).addAttribute("priorities", priorities);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenCreateTaskThanSuccess() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        Task task = new Task();
        int priorityId = 1;
        List<String> categoriesId = List.of("1");
        HttpSession httpSession = mock(HttpSession.class);
        Priority priority = new Priority();
        priority.setName("not urgent");
        Category category = new Category();
        category.setName("java");
        when(hibernatePriorityService.findById(priorityId)).thenReturn(Optional.of(priority));
        when(hibernateCategoryService.findByIds(categoriesId)).thenReturn(List.of(category));
        String expected = "redirect:/todo";
        String page = taskController.createTask(model, task, priorityId, categoriesId, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenCreateTaskThanNotSuccess() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        Task task = new Task();
        int priorityId = 1;
        List<String> categoriesId = List.of("1");
        HttpSession httpSession = mock(HttpSession.class);
        when(hibernatePriorityService.findById(priorityId)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.createTask(model, task, priorityId, categoriesId, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenOpenTaskThenSuccess() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        boolean success = true;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(hibernateTaskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "task/task";
        String page = taskController.openTask(model, id, success, httpSession);
        verify(model).addAttribute("task", task);
        verify(model).addAttribute("success", success);
        verify(model).addAttribute("user", user);
        assertThat(expected).isEqualTo(page);
    }

    @Test
    public void whenOpenTaskThenNotSuccess() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        boolean success = true;
        HttpSession httpSession = mock(HttpSession.class);
        when(hibernateTaskService.findById(id)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.openTask(model, id, success, httpSession);
        assertThat(expected).isEqualTo(page);
    }

    @Test
    public void whenChangeStatusThenSuccessToFalse() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        task.setDone(true);
        when(hibernateTaskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "redirect:/openTask/" + id + "?success=true";
        String page = taskController.changeStatus(model, id, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenChangeStatusThenSuccessToTrue() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        task.setDone(false);
        when(hibernateTaskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "redirect:/openTask/" + id + "?success=true";
        String page = taskController.changeStatus(model, id, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenChangeStatusThenNotSuccess() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(hibernateTaskService.findById(id)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.changeStatus(model, id, httpSession);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenEditDescriptionThenSuccess() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(hibernateTaskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "task/editDescription";
        String page = taskController.editDescription(model, id, httpSession);
        verify(model).addAttribute("task", task);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenEditDescriptionThenNotSuccess() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(hibernateTaskService.findById(id)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.editDescription(model, id, httpSession);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenEdit() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        Task task = new Task();
        task.setId(1);
        task.setDescription("test");
        String expected = "redirect:/openTask/" + task.getId();
        String page = taskController.edit(task);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenDelete() {
        HibernateTaskService hibernateTaskService = mock(HibernateTaskService.class);
        HibernatePriorityService hibernatePriorityService = mock(HibernatePriorityService.class);
        HibernateCategoryService hibernateCategoryService = mock(HibernateCategoryService.class);
        TaskController taskController = new TaskController(
                hibernateTaskService, hibernatePriorityService, hibernateCategoryService
        );
        int id = 1;
        String expected = "redirect:/todo?delete=true";
        String page = taskController.delete(id);
        assertThat(page).isEqualTo(expected);
    }
}