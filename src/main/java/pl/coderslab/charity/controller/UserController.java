package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.coderslab.charity.authentication_model.CurrentUser;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.authentication.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
@SessionAttributes("username")
public class UserController {

    private DonationService donationService;
    private InstitutionService institutionService;

    @Autowired
    public UserController(DonationService donationService,
                          InstitutionService institutionService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
    }

    @ModelAttribute("institutions")
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @RequestMapping("")
    public String getUserlandingPage(@AuthenticationPrincipal CurrentUser user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("sumOfUserQuantities", donationService.getUserSumofDonationQuantity(user.getUser()));
        model.addAttribute("userInstitutionDonatedCount", institutionService.getUserInstitutionDonatedCount(user.getUser()));
        return "user-landing-page";
    }

}
