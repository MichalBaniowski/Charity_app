package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.exception.ResourceNotFoundException;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.authentication.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Donation> getAllUserDonationsOrderByStatusReceived(User user) {
        return donationRepository.findAllByUser(user).stream()
                .sorted(DonationService::sortedByStatusReceived)
                .collect(Collectors.toList());
    }

    public List<Donation> getAllUserDonationsOrderByStatusNotReceived(User user) {
        return donationRepository.findAllByUser(user).stream()
                .sorted(DonationService::sortedByStatusNotReceived)
                .collect(Collectors.toList());
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public List<Donation> getAllDonationsByInstitution(Institution institution) {
        return donationRepository.findAllByInstitution(institution);
    }

    public Donation getDonationById(Long id, User user) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found"));
        if (donation.getUser().getId() == user.getId() ||
                user.getRoles().contains(roleRepository.findByName("ROLE_ADMIN"))) {
            return donation;
        }
        throw new AccessDeniedException("Access denied");
    }

    public boolean setReceivedStatus(Long id, User user) {
        Donation donation = getDonationById(id, user);
        donation.setStatus(true);
        donationRepository.save(donation);
        return true;
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

    static private int sortedByStatusReceived(Donation d1, Donation d2) {
        if (d1.isStatus() && !d2.isStatus()) return -1;
        else if (!d1.isStatus() && d2.isStatus()) return 1;
        else {
            return sortIfStatusEqual(d1, d2);
        }
    }

    static private int sortedByStatusNotReceived(Donation d1, Donation d2) {
        if (d1.isStatus() && !d2.isStatus()) return 1;
        else if (!d1.isStatus() && d2.isStatus()) return -1;
        else {
            return sortIfStatusEqual(d1, d2);
        }
    }

    static private int sortIfStatusEqual(Donation d1, Donation d2) {
        if (d1.isStatus()) {
            if (d1.getPickUpDate().isAfter(d2.getPickUpDate())) return -1;
            else if (d1.getPickUpDate().isBefore(d2.getPickUpDate())) return 1;
            else return sortedByCeationDate(d1, d2);
        } else {
            return sortedByCeationDate(d1, d2);
        }
    }

    static private int sortedByCeationDate(Donation d1, Donation d2) {
        if (d1.getCreated().isAfter(d2.getCreated())) return -1;
        else if (d1.getCreated().isBefore(d2.getCreated())) return 1;
        else return 0;
    }

}
