package pl.coderslab.charity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.service.authentication.UserService;
import pl.coderslab.charity.validator.ValidationByUserGroup;

import javax.validation.Valid;

@Controller
public class UserLoginController {
    private UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Validated({ValidationByUserGroup.class}) User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "register";
        }
        String prompt = userService.saveUser(user) ? "Udało się storzyć konto" : "Nie udało się stworzyć konta";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

}
