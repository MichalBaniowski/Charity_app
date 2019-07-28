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
import pl.coderslab.charity.exception.ActionForbiddenException;
import pl.coderslab.charity.service.authentication.RoleService;
import pl.coderslab.charity.service.authentication.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/s_admin/admins")
public class SuperAdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public SuperAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getAdmins(Model model) {
        model.addAttribute("admins", userService.findAllAdmins());
        return "admins";
    }

    @GetMapping("/{id}")
    public String getAdminById(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.getAllRolesButSuperAdmin());
        return "admin-details";
    }

    @PostMapping("/edit")
    public String editAdmin(@Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "admin-details";
        }
        String prompt = userService.updateUserByAdmin(user) ?
                "Zapisano zmianę" : "Nie udało się wprowadzić zmian";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

    @RequestMapping("/{id}/delete")
    public String deleteAdmin(@PathVariable Long id, Model model) {
        try{
            String prompt = userService.deleteUser(userService.findById(id)) ?
                    "Administrator usunięty" : "Nie udało się usunąć administratora";
            model.addAttribute("prompt", prompt);
        }catch (ActionForbiddenException e) {
            String prompt = e.getMessage();
            model.addAttribute("prompt", prompt);
        }
        return "result-prompt";
    }




}
