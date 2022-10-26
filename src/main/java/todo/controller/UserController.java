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

import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/formRegistrationUser")
    public String formRegistrationUser(Model model, @RequestParam(
            name = "registrationSuccess", required = false
    ) Boolean registrationSuccess) {
        model.addAttribute("registrationSuccess", registrationSuccess);
        return "registrationUser";
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
    ) Boolean loginSuccess) {
        model.addAttribute("loginSuccess", loginSuccess);
        return "loginUser";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user) {
        Optional<User> loginUser = userService.findByLoginAndPwd(user);
        if (loginUser.isEmpty()) {
            return "redirect:/formLoginUser?loginSuccess=false";
        }
        return "redirect:/todo";
    }
}
