package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.util.SessionUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Test
    public void whenGetTasksThanGetAllTasks() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
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
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
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
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
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
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
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
        when(categoryService.findAll()).thenReturn(categories);
        when(priorityService.findAll()).thenReturn(priorities);
        String expected = "task/addTask";
        String page = taskController.addTask(model, httpSession);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("categories", categories);
        verify(model).addAttribute("priorities", priorities);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenCreateTaskThanSuccess() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
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
        when(priorityService.findById(priorityId)).thenReturn(Optional.of(priority));
        when(categoryService.findByIds(categoriesId)).thenReturn(List.of(category));
        String expected = "redirect:/todo";
        String page = taskController.createTask(model, task, priorityId, categoriesId, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenCreateTaskThanNotSuccess() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
        );
        Model model = mock(Model.class);
        Task task = new Task();
        int priorityId = 1;
        List<String> categoriesId = List.of("1");
        HttpSession httpSession = mock(HttpSession.class);
        when(priorityService.findById(priorityId)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.createTask(model, task, priorityId, categoriesId, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenOpenTaskThenSuccess() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        boolean success = true;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(taskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "task/task";
        String page = taskController.openTask(model, id, success, httpSession);
        verify(model).addAttribute("task", task);
        verify(model).addAttribute("success", success);
        verify(model).addAttribute("user", user);
        assertThat(expected).isEqualTo(page);
    }

    @Test
    public void whenOpenTaskThenNotSuccess() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        boolean success = true;
        HttpSession httpSession = mock(HttpSession.class);
        when(taskService.findById(id)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.openTask(model, id, success, httpSession);
        assertThat(expected).isEqualTo(page);
    }

    @Test
    public void whenChangeStatusThenSuccessToFalse() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        task.setDone(true);
        when(taskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "redirect:/openTask/" + id + "?success=true";
        String page = taskController.changeStatus(model, id, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenChangeStatusThenSuccessToTrue() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        task.setDone(false);
        when(taskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "redirect:/openTask/" + id + "?success=true";
        String page = taskController.changeStatus(model, id, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenChangeStatusThenNotSuccess() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(taskService.findById(id)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.changeStatus(model, id, httpSession);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenEditDescriptionThenSuccess() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(taskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "task/editDescription";
        String page = taskController.editDescription(model, id, httpSession);
        verify(model).addAttribute("task", task);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenEditDescriptionThenNotSuccess() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(taskService.findById(id)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.editDescription(model, id, httpSession);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenEdit() {
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
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
        TaskService taskService = mock(TaskService.class);
        PriorityService priorityService = mock(PriorityService.class);
        CategoryService categoryService = mock(CategoryService.class);
        TaskController taskController = new TaskController(
                taskService, priorityService, categoryService
        );
        int id = 1;
        String expected = "redirect:/todo?delete=true";
        String page = taskController.delete(id);
        assertThat(page).isEqualTo(expected);
    }
}