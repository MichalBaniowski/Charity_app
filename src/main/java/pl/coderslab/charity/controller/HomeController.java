package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import java.util.List;


@Controller
public class HomeController {

    private DonationService donationService;
    private InstitutionService institutionService;

    @Autowired
    public HomeController(DonationService donationService, InstitutionService institutionService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
    }

    @ModelAttribute("sumOfQuantities")
    public Integer getSumOfQuantities() {
        return donationService.getSumOfDonationQuantity();
    }

    @ModelAttribute("institutions")
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @ModelAttribute("institutionDonatedCount")
    public Integer getInstitutionDonatedCount() {
        return institutionService.getInstitutionDonatedCount();
    }
    @RequestMapping("/")
    public String homeAction(Model model) {
        return "index";
    }
}
