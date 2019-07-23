package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.authentication_model.CurrentUser;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("user/donation")
public class DonationController {

    private CategoryService categoryService;
    private InstitutionService institutionService;
    private DonationService donationService;

    @Autowired
    public DonationController(CategoryService categoryService,
                              InstitutionService institutionService,
                              DonationService donationService) {
        this.categoryService = categoryService;
        this.institutionService = institutionService;
        this.donationService = donationService;
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @ModelAttribute("institutions")
    public List<Institution> getInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @GetMapping("")
    public String getAllUserDonations(Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("donations", donationService.getAllUserDonations(user.getUser()));
        return "user-donations";
    }

    @GetMapping("/add")
    public String getDonationForm(Model model) {
        model.addAttribute("donation", new Donation());
        return "form";
    }

    @PostMapping("/add")
    public String processDonationForm(@Valid Donation donation,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal CurrentUser user,
                                      Model model) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        donation.setUser(user.getUser());
        String prompt = donationService.saveDonation(donation) ?
                "Udało się dodać darowiznę" : "Nie udało się dodać darowizny";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }
}
