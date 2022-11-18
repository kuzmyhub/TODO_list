package ru.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.todo.model.Category;
import ru.todo.model.Priority;
import ru.todo.model.Task;
import ru.todo.model.User;
import ru.todo.service.SimpleCategoryService;
import ru.todo.service.SimplePriorityService;
import ru.todo.service.SimpleTaskService;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Test
    public void whenGetTasksThanGetAllTasks() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
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
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
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
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
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
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
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
        when(simpleCategoryService.findAll()).thenReturn(categories);
        when(simplePriorityService.findAll()).thenReturn(priorities);
        String expected = "task/addTask";
        String page = taskController.addTask(model, httpSession);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("categories", categories);
        verify(model).addAttribute("priorities", priorities);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenCreateTaskThanSuccess() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        Model model = mock(Model.class);
        Task task = new Task();
        int priorityId = 1;
        List<Integer> categoriesId = List.of(1);
        HttpSession httpSession = mock(HttpSession.class);
        Priority priority = new Priority();
        priority.setName("not urgent");
        Category category = new Category();
        category.setName("java");
        when(simplePriorityService.findById(priorityId)).thenReturn(Optional.of(priority));
        when(simpleCategoryService.findByIds(categoriesId)).thenReturn(List.of(category));
        String expected = "redirect:/todo";
        String page = taskController.createTask(model, task, priorityId, categoriesId, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenCreateTaskThanNotSuccess() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        Model model = mock(Model.class);
        Task task = new Task();
        int priorityId = 1;
        List<Integer> categoriesId = List.of(1);
        HttpSession httpSession = mock(HttpSession.class);
        when(simplePriorityService.findById(priorityId)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.createTask(model, task, priorityId, categoriesId, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenOpenTaskThenSuccess() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        boolean success = true;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(simpleTaskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "task/task";
        String page = taskController.openTask(model, id, success, httpSession);
        verify(model).addAttribute("task", task);
        verify(model).addAttribute("success", success);
        verify(model).addAttribute("user", user);
        assertThat(expected).isEqualTo(page);
    }

    @Test
    public void whenOpenTaskThenNotSuccess() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        boolean success = true;
        HttpSession httpSession = mock(HttpSession.class);
        when(simpleTaskService.findById(id)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.openTask(model, id, success, httpSession);
        assertThat(expected).isEqualTo(page);
    }

    @Test
    public void whenChangeStatusThenSuccessToFalse() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        task.setDone(true);
        when(simpleTaskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "redirect:/openTask/" + id + "?success=true";
        String page = taskController.changeStatus(model, id, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenChangeStatusThenSuccessToTrue() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        task.setDone(false);
        when(simpleTaskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "redirect:/openTask/" + id + "?success=true";
        String page = taskController.changeStatus(model, id, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenChangeStatusThenNotSuccess() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(simpleTaskService.findById(id)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.changeStatus(model, id, httpSession);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenEditDescriptionThenSuccess() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        Task task = new Task();
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(simpleTaskService.findById(id)).thenReturn(Optional.of(task));
        String expected = "task/editDescription";
        String page = taskController.editDescription(model, id, httpSession);
        verify(model).addAttribute("task", task);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenEditDescriptionThenNotSuccess() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        Model model = mock(Model.class);
        int id = 1;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
        when(simpleTaskService.findById(id)).thenReturn(Optional.empty());
        String expected = "task/404";
        String page = taskController.editDescription(model, id, httpSession);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenEdit() {
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
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
        SimpleTaskService simpleTaskService = mock(SimpleTaskService.class);
        SimplePriorityService simplePriorityService = mock(SimplePriorityService.class);
        SimpleCategoryService simpleCategoryService = mock(SimpleCategoryService.class);
        TaskController taskController = new TaskController(
                simpleTaskService, simplePriorityService, simpleCategoryService
        );
        int id = 1;
        String expected = "redirect:/todo?delete=true";
        String page = taskController.delete(id);
        assertThat(page).isEqualTo(expected);
    }
}