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
@RequestMapping("/user/donations")
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
    public String getAllUserDonations(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("donations", donationService.getAllUserDonations(currentUser.getUser()));
        return "user-donations";
    }

    @GetMapping("/{id}")
    public String getUserByid(Model model, @PathVariable Long id, @AuthenticationPrincipal CurrentUser currentUser) {
        try {
            Donation donation = donationService.getDonationById(id, currentUser.getUser());
            model.addAttribute("donation", donation);
            return "user-donation-details";
        } catch (Exception e) {
            model.addAttribute("prompt", e.getMessage());
            return "result-prompt";
        }
    }

    @GetMapping("/add")
    public String getDonationForm(Model model) {
        model.addAttribute("donation", new Donation());
        return "donation-form";
    }

    @PostMapping("/add")
    public String processDonationForm(@Valid Donation donation,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal CurrentUser user,
                                      Model model) {
        if (bindingResult.hasErrors()) {
            return "donation-form";
        }
        donation.setUser(user.getUser());
        String prompt = donationService.saveDonation(donation) ?
                "Udało się dodać darowiznę" : "Nie udało się dodać darowizny";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

    @GetMapping("/{id}/edit")
    public String getDonationEditForm(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        try {
            Donation donation = donationService.getDonationById(id, currentUser.getUser());//TODO check status
            model.addAttribute("donation", donation);
            return "donation-form";
        } catch (Exception e) {
            model.addAttribute("prompt", e.getMessage());
            return "result-prompt";
        }
    }

    @PostMapping("/edit")
    public String processDonationEditForm(@Valid Donation donation,
                                          BindingResult bindingResult,
                                          Model model) {
        if (bindingResult.hasErrors()) {
            return "donation-form";
        }
        String prompt = donationService.saveDonation(donation) ?
                "Udało się dodać darowiznę" : "Nie udało się dodać darowizny";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

    @RequestMapping("/{id}/cancel")
    public String cancelDonation(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        try {
            String prompt = donationService.deleteDonation(id, currentUser.getUser()) ?
                    "Usunięto dotację" : "Udało się usunąć dotację";
            model.addAttribute("prompt", prompt);
        } catch (Exception e) {
            model.addAttribute("prompt", e.getMessage());
        }
        return "result-prompt";
    }

}
