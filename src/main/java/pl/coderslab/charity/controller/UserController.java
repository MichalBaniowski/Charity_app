package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.authentication_model.CurrentUser;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.authentication.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
@SessionAttributes("username")
public class UserController {

    private DonationService donationService;
    private InstitutionService institutionService;
    private UserService userService;

    @Autowired
    public UserController(DonationService donationService,
                          InstitutionService institutionService,
                          UserService userService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.userService = userService;
    }

    @ModelAttribute("institutions")
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @RequestMapping("")
    public String getUserlandingPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("sumOfUserQuantities", donationService.getUserSumofDonationQuantity(currentUser.getUser()));
        model.addAttribute("userInstitutionDonatedCount", institutionService.getUserInstitutionDonatedCount(currentUser.getUser()));
        return "user-landing-page";
    }

    @GetMapping("/edit")
    public String getEditForm(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("user", currentUser.getUser());
        return "user-form";
    }

    @PostMapping("/edit")
    public String processEditForm(@AuthenticationPrincipal CurrentUser currentUser,
                                  @Valid User user,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        String prompt = userService.updateUser(user) ?
                    "Zapisano zmiany" : "Nie udało się zapisać zmian";
        //update email, password, username ?
        currentUser.setUser(user);
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }
    @RequestMapping("/delete")
    public String delete(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        try{
            userService.deleteUser(currentUser.getUser());
            return "redirect:/logout";
        }catch (RuntimeException e) {
            model.addAttribute("prompt", e.getMessage());
            return "result-prompt";
        }
    }

}
