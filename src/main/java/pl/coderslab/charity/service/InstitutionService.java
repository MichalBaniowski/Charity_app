package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.entity.Institution;
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

    public Integer getInstitutionDonatedCount() {
        return institutionRepository.getInstitutionDonatedCount();
    }

    public Integer getUserInstitutionDonatedCount(User user) {
        return institutionRepository.getUserInstitutionDoantedCount(user);
    }
}
