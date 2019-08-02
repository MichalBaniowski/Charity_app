package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.exception.ResourceNotFoundException;
import pl.coderslab.charity.security.model.LoggedUser;
import pl.coderslab.charity.service.ActivationService;
import pl.coderslab.charity.service.authentication.UserService;

@Controller
public class ActivationController {

    private UserService userService;
    private ActivationService activationService;

    @Autowired
    public ActivationController(UserService userService, ActivationService activationService) {
        this.userService = userService;
        this.activationService = activationService;
    }

    @RequestMapping("/activation")
    public String activateUser(@RequestParam Long userId,
                               @RequestParam String code,
                               Model model,
                               @AuthenticationPrincipal LoggedUser currentUser) {
        User user = userService.findById(userId);
        if(user == null) {
            throw new ResourceNotFoundException("user not found");
        }
        String prompt;
        if (activationService.checkCode(code, user) &&
            userService.activateUser(userId)) {
            prompt = "Udało się aktywować konto";
            if(currentUser != null) {
                currentUser.getUser().setEnabled(true);
            }
        }else {
            prompt = "Nie udało się aktywować konta";
        }
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }
}
