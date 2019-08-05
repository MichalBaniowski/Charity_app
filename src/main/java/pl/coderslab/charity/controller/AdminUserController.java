package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.authentication.Role;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.security.model.LoggedUser;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.authentication.RoleService;
import pl.coderslab.charity.service.authentication.UserService;
import pl.coderslab.charity.validator.ValidationByAdminGroup;

import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes("adminMode")
public class AdminUserController {

    private UserService userService;
    private RoleService roleService;
    private InstitutionService institutionService;
    private final String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";

    @Autowired
    public AdminUserController(UserService userService, RoleService roleService, InstitutionService institutionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.institutionService = institutionService;
    }

    @ModelAttribute("institutions")
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @ModelAttribute("userCount")
    public Integer getUserCount() {
        return userService.getUserCount();
    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.getAllRolesButSuperAdmin();
    }

    @RequestMapping("")
    public String getAdminLandingPage(Model model) {
        model.addAttribute("adminMode", true);
        return "admin-landing-page";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getUser(Model model, @PathVariable Long id, @AuthenticationPrincipal LoggedUser currentUser) {
        model.addAttribute("user", userService.findById(id));

        if (currentUser.getUser().getRoles().contains(roleService.findByRole(SUPER_ADMIN_ROLE))) {
            model.addAttribute("superAdmin", true);
        }
        return "user-details";
    }

    @PostMapping("/users/edit")
    public String editUser(@Validated({ValidationByAdminGroup.class}) User user,
                           BindingResult bindingResult,
                           Model model,
                           @AuthenticationPrincipal LoggedUser currentUser) {

        if (bindingResult.hasErrors()) {
            if (currentUser.getUser().getRoles().contains(roleService.findByRole(SUPER_ADMIN_ROLE))) {
                model.addAttribute("superAdmin", true);
            }
            return "user-details";
        }
        String prompt = userService.updateUserByAdmin(user) ? "Zapisano zmiany" : "Nie udało się zapisać zmian";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

    @RequestMapping("/users/{id}/delete")
    public String deleteUser(Model model, @PathVariable Long id) {
        String prompt = userService.deleteUser(userService.findById(id)) ?
                "Użytkownik usunięty" : "Nie udało się usunąć użytkownika";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }
}
