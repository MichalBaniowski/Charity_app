package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.exception.ResourceNotFoundException;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/institutions")
public class InstitutionController {
    private DonationService donationService;
    private InstitutionService institutionService;

    @Autowired
    public InstitutionController(DonationService donationService, InstitutionService institutionService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
    }

    @GetMapping("")
    public String getAllInstitutions(Model model) {
        model.addAttribute("institutions", institutionService.getAllInstitutions());
        return "institutions";
    }

    @GetMapping("/{id}")
    public String getInstitution(Model model, @PathVariable Long id) {
        try {
            Institution institution = institutionService.findInstitutionById(id);
            model.addAttribute("institution", institution);
            model.addAttribute("donations", donationService.getAllDonationsByInstitution(institution));
            return "institution-details";
        } catch (ResourceNotFoundException e) {
            model.addAttribute("prompt", e.getMessage());
            return "result-prompt";
        }

    }

    @GetMapping("/create")
    public String getInstitutionForm(Model model) {
        model.addAttribute("institution", new Institution());
        return "institution-form";
    }

    @PostMapping("/create")
    public String processInstitutionForm(@Valid Institution institution,
                                         BindingResult bindingResult,
                                         Model model) {
        if (bindingResult.hasErrors()) {
            return "institution-form";
        }
        String prompt = institutionService.saveInstitution(institution) ?
                "Udało się utworzyć organizację" : "Nie udało się utworzyć organizacji";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

    @GetMapping("/{id}/edit")
    public String getInstitutionEditForm(Model model, @PathVariable Long id) {
        try {
            Institution institution = institutionService.findInstitutionById(id);
            model.addAttribute("institution", institution);
            return "institution-edit-form";
        } catch (ResourceNotFoundException e) {
            model.addAttribute("prompt", e.getMessage());
            return "result-prompt";
        }

    }

    @PostMapping("/{id}/edit")
    public String proccessInstitutionEditForm(@Valid Institution institution,
                                              BindingResult bindingResult,
                                              @PathVariable Long id,
                                              Model model) {
        if (bindingResult.hasErrors()) {
            return "institution-edit-form";
        }
        String prompt = institutionService.saveInstitution(institution) ?
                "Zapisano zmiany" : "Nie udało się zapisać zmian";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

    @GetMapping("/{id}/delete")
    public String deleteInstitution(Model model, @PathVariable Long id) {
        String prompt = institutionService.deleteInstitution(id) ?
                "Organizacja usunięta" : "Nie udało się usunąć organizacji";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }
}
