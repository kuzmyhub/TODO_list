package todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import todo.model.User;
import todo.service.UserService;
import todo.util.SessionUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/formRegistrationUser")
    public String formRegistrationUser(Model model, @RequestParam(
            name = "registrationSuccess", required = false
    ) Boolean registrationSuccess, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("registrationSuccess", registrationSuccess);
        model.addAttribute("user", user);
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> addedUser = userService.add(user);
        if (addedUser.isEmpty()) {
            return "redirect:/formRegistrationUser?registrationSuccess=false";
        }
        return "redirect:/formRegistrationUser?registrationSuccess=true";
    }

    @GetMapping("/formLoginUser")
    public String formLoginUser(Model model, @RequestParam(
            name = "loginSuccess", required = false
    ) Boolean loginSuccess, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("loginSuccess", loginSuccess);
        model.addAttribute("user", user);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user,
                        HttpServletRequest req) {
        Optional<User> loginUser = userService.findByLoginAndPassword(user);
        if (loginUser.isEmpty()) {
            return "redirect:/formLoginUser?loginSuccess=false";
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", loginUser.get());
        return "redirect:/todo";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/formLoginUser";
    }
}
