package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.service.authentication.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @GetMapping("/{id}")
    public String getUser(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.findById(id));
        return "user-details";
    }

    @RequestMapping("/activate/{id}")
    public String activateUser(Model model, @PathVariable Long id) {
        String prompt = userService.activateUser(userService.findById(id)) ?
                    "Użytkownik aktywny" : "Użytkownik nieaktywny";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

    @RequestMapping("/deactivate/{id}")
    public String deactivateUser(Model model, @PathVariable Long id) {
        String prompt = userService.deactivateUser(userService.findById(id)) ?
                "Użytkownik nieaktywny" : "Użytkownik aktywny";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable Long id) {
        String prompt = userService.deleteUser(userService.findById(id)) ?
                "Użytkownik usunięty" : "Nie udało się usunąć użytkownika";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }
}
