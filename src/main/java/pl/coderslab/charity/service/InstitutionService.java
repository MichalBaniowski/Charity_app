package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.exception.ElementNotFoundException;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;

@Service
public class InstitutionService {

    private InstitutionRepository institutionRepository;
    private DonationRepository donationRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository, DonationRepository donationRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
    }

    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    public Institution findInstitutionById(Long id) {
        return institutionRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Institution not found"));
    }

    public boolean saveInstitution(Institution institution) {
        return institutionRepository.save(institution).getId() != null;
    }

    public boolean deleteInstitution(Long id) {
        donationRepository.deleteAllByInstitution(institutionRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Institution not found")));
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
