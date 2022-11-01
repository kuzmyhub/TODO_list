package ru.job4j.todo.util;


import lombok.AllArgsConstructor;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
public final class SessionUser {

    public static User getSession(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}
