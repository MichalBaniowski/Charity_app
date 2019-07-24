package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.exception.ElementNotFoundException;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;

@Service
public class InstitutionService {

    private InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    public Institution findInstitutionById(Long id) {
        return institutionRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Institution not found"));
    }

    public boolean saveInstitution(Institution institution) {
        return institutionRepository.save(institution).getId() != null;
    }

    public boolean deleteInstitution(Long id) {
        institutionRepository.deleteById(id);
        return !institutionRepository.existsById(id);
    }

    public Integer getInstitutionDonatedCount() {
        return institutionRepository.getInstitutionDonatedCount();
    }

    public Integer getUserInstitutionDonatedCount(User user) {
        return institutionRepository.getUserInstitutionDoantedCount(user);
    }
}
