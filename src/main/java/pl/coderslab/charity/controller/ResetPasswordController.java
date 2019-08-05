package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.service.AccountService;
import pl.coderslab.charity.service.authentication.UserService;

import javax.validation.Valid;

@Controller
public class ResetPasswordController {

    private UserService userService;
    private AccountService accountService;

    @Autowired
    public ResetPasswordController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping("/forgot-password")
    public String getEmailForm() {
        return "email-form";
    }

    @PostMapping("/forgot-password")
    public String processEmailForm(@RequestParam String email, Model model) {
        if(!userService.existsByEmail(email)) {
            model.addAttribute("errorMessage", "nie ma takiego użytkownika");
            return "email-form";
        }
        userService.sendRestPasswordLink(email);
        model.addAttribute("prompt", "na podany email wysłano link do zmiany hasła");
        return "result-prompt";
    }

    @GetMapping("/reset-password")
    public String getResetPasswordForm(@RequestParam Long userId,
                                       @RequestParam String token,
                                       Model model) {
        User user = userService.findById(userId);
        if(accountService.checkToken(token, user)) {
            model.addAttribute("user", user);
            return "edit-password-form";
        }
        model.addAttribute("prompt", "nie udało się zmienić hasła");
        return "result-prompt";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@Valid User user,
                                       BindingResult bindingResult,
                                       Model model) {
        if(bindingResult.hasErrors()) {
            return "edit-password-form";
        }
        userService.resetPassword(user);
        model.addAttribute("prompt", "Hasło zostało zmienione");
        return "result-prompt";
    }
}
