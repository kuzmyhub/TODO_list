package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.HibernateUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    public void whenFormRegistrationUser() {
        HibernateUserService hibernateUserService = mock(HibernateUserService.class);
        UserController userController = new UserController(hibernateUserService);
        Model model = mock(Model.class);
        Boolean registrationSuccess = true;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
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
        HibernateUserService hibernateUserService = mock(HibernateUserService.class);
        UserController userController = new UserController(hibernateUserService);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        when(hibernateUserService.add(any(User.class))).thenReturn(Optional.of(user));
        String expected = "redirect:/formRegistrationUser?registrationSuccess=true";
        String page = userController.registration(user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenRegistrationThenNotSuccess() {
        HibernateUserService hibernateUserService = mock(HibernateUserService.class);
        UserController userController = new UserController(hibernateUserService);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        when(hibernateUserService.add(any(User.class))).thenReturn(Optional.empty());
        String expected = "redirect:/formRegistrationUser?registrationSuccess=false";
        String page = userController.registration(user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenFormLoginUser() {
        HibernateUserService hibernateUserService = mock(HibernateUserService.class);
        UserController userController = new UserController(hibernateUserService);
        Model model = mock(Model.class);
        Boolean loginSuccess = true;
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Гость");
        user.setUtc("UTC+3");
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
        HibernateUserService hibernateUserService = mock(HibernateUserService.class);
        UserController userController = new UserController(hibernateUserService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        when(hibernateUserService.findByLoginAndPassword(
                any(String.class), any(String.class)
        )).thenReturn(Optional.of(user));
        when(req.getSession()).thenReturn(httpSession);
        String expected = "redirect:/todo";
        String page = userController.login(user, req);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenLoginThenNotSuccess() {
        HibernateUserService hibernateUserService = mock(HibernateUserService.class);
        UserController userController = new UserController(hibernateUserService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        when(hibernateUserService.findByLoginAndPassword(
                any(String.class), any(String.class)
        )).thenReturn(Optional.empty());
        when(req.getSession()).thenReturn(httpSession);
        String expected = "redirect:/formLoginUser?loginSuccess=false";
        String page = userController.login(user, req);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenLogout() {
        HibernateUserService hibernateUserService = mock(HibernateUserService.class);
        UserController userController = new UserController(hibernateUserService);
        HttpSession httpSession = mock(HttpSession.class);
        String expected = "redirect:/formLoginUser";
        String page = userController.logout(httpSession);
        assertThat(page).isEqualTo(expected);
    }
}