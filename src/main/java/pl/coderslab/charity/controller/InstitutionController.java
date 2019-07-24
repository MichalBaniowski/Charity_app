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
import pl.coderslab.charity.service.InstitutionService;

import javax.validation.Valid;

@Controller
@RequestMapping("admin/institution")
public class InstitutionController {
    @Autowired
    private InstitutionService institutionService;

    @GetMapping("")
    public String getAllInstitutions(Model model) {
        model.addAttribute("institutions", institutionService.getAllInstitutions());
        return "institutions";
    }

    @GetMapping("/{id}")
    public String getInstitution(Model model, @PathVariable Long id) {
        model.addAttribute("institution", institutionService.findInstitutionById(id));
        return "institution-details";
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

    @GetMapping("/edit/{id}")
    public String getInstitutionEditForm(Model model, @PathVariable Long id) {
        Institution institution = institutionService.findInstitutionById(id);
        model.addAttribute("institution", institution);
        return "institution-form";
    }

    @PostMapping("/edit/{id}")
    public String proccessInstitutionEditForm(@Valid Institution institution,
                                              BindingResult bindingResult,
                                              @PathVariable Long id,
                                              Model model) {
        if(bindingResult.hasErrors()) {
            return "institution-form";
        }
        String prompt = institutionService.saveInstitution(institution) ?
                "Zapisano zmiany" : "Nie udało się zapisać zmian";
        model.addAttribute("prompt", prompt);
        return "result-prompt";
    }

    @GetMapping("/delete/{id}")
    public String deleteInstitution(Model model, @PathVariable Long id) {
        String prompt = institutionService.deleteInstitution(id) ?
                "Organizacja usunięta" : "Nie udało się usunąć organizacji";
        model.addAttribute("prompt", prompt);
        return"result-prompt";
    }
}
