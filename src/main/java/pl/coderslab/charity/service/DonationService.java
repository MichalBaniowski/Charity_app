package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.List;

@Service
public class DonationService {

    private DonationRepository donationRepository;
    private CategoryRepository categoryRepository;
    @Autowired
    public DonationService(DonationRepository donationRepository,
                           CategoryRepository categoryRepository) {
        this.donationRepository = donationRepository;
        this.categoryRepository = categoryRepository;
    }

    public Integer getSumOfDonationQuantity(){
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
    public boolean saveDonation (Donation donation) {
        try {
            donationRepository.save(donation);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
