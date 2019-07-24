package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.service.authentication.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/s_admin/admins")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String getAdmins(Model model) {
        model.addAttribute("admins", userService.findAllAdmins());
        return "admins";
    }

    @GetMapping("/{id}")
    public String getAdminById(Model model, @PathVariable Long id) {
        model.addAttribute("admin", userService.findById(id));
        return "admin-detail";
    }

    @GetMapping("/edit/{id}")
    public String getEditAdminForm(Model model, @PathVariable Long id) {
        model.addAttribute("admin", userService.findById(id));
        return "admin-form";
    }

    @PostMapping("/edit/{id}")
    public String processAdminForm(@Valid User user,
                                   BindingResult bindingResult,
                                   Model model,
                                   @PathVariable Long id) {
        if(bindingResult.hasErrors()) {
            return "admin-form";
        }
        String prompt = userService.saveUser(user) ?
                "Ustawienia zapisane" : "Nie udało się zapisać zmian";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }


}
