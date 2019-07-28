package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.authentication_model.Role;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.exception.ElementNotFoundException;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.authentication.RoleRepository;

import java.util.List;

@Service
public class DonationService {

    private DonationRepository donationRepository;
    private CategoryRepository categoryRepository;
    private RoleRepository roleRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository,
                           CategoryRepository categoryRepository,
                           RoleRepository roleRepository) {
        this.donationRepository = donationRepository;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
    }

    public Integer getSumOfDonationQuantity() {
        return donationRepository.getSumOfQuantity();
    }

    public Integer getUserSumofDonationQuantity(User user) {
        Integer userSumOfQuantity = donationRepository.getUserSumOfQuantity(user);
        return userSumOfQuantity == null ? 0 : userSumOfQuantity;
    }

    public List<Donation> getAllUserDonations(User user) {
        return donationRepository.findAllByUser(user);
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public List<Donation> getAllDonationsByInstitution(Institution institution) {
        return donationRepository.findAllByInstitution(institution);
    }

    public Donation getDonationById(Long id, User user) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Donation not found"));
        if (donation.getUser().getId() == user.getId() ||
                user.getRoles().contains(roleRepository.findByName("ROLE_ADMIN"))) {
            return donation;
        }
        throw new AccessDeniedException("Access denied");
    }

    public boolean deleteDonation(Long id, User user) {
        Donation donation = getDonationById(id, user);
        donationRepository.delete(donation);
        return !donationRepository.existsById(id);
    }

    public boolean saveDonation(Donation donation) {
        try {
            donationRepository.save(donation);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
