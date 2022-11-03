package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    public void whenFormRegistrationUser() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        Model model = mock(Model.class);
        Boolean registrationSuccess = true;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        String expected = "user/registration";
        String page = userController.formRegistrationUser(
                model, registrationSuccess, httpSession
        );
        verify(model).addAttribute("registrationSuccess", registrationSuccess);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenRegistrationThenSuccess() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        when(userService.add(any(User.class))).thenReturn(Optional.of(user));
        String expected = "redirect:/formRegistrationUser?registrationSuccess=true";
        String page = userController.registration(user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenRegistrationThenNotSuccess() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        when(userService.add(any(User.class))).thenReturn(Optional.empty());
        String expected = "redirect:/formRegistrationUser?registrationSuccess=false";
        String page = userController.registration(user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenFormLoginUser() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        Model model = mock(Model.class);
        Boolean loginSuccess = true;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        String expected = "user/login";
        String page = userController.formLoginUser(
                model, loginSuccess, httpSession
        );
        verify(model).addAttribute("loginSuccess", loginSuccess);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenLoginThenSuccess() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        when(userService.findByLoginAndPassword(
                any(String.class), any(String.class)
        )).thenReturn(Optional.of(user));
        when(req.getSession()).thenReturn(httpSession);
        String expected = "redirect:/todo";
        String page = userController.login(user, req);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenLoginThenNotSuccess() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        when(userService.findByLoginAndPassword(
                any(String.class), any(String.class)
        )).thenReturn(Optional.empty());
        when(req.getSession()).thenReturn(httpSession);
        String expected = "redirect:/formLoginUser?loginSuccess=false";
        String page = userController.login(user, req);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenLogout() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        HttpSession httpSession = mock(HttpSession.class);
        String expected = "redirect:/formLoginUser";
        String page = userController.logout(httpSession);
        assertThat(page).isEqualTo(expected);
    }
}