package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findAllByCategoriesIn(List<Category> categories);
    List<Donation> findAllByUser(User user);

    @Query("SELECT SUM(d.quantity) FROM Donation d")
    Integer getSumOfQuantity();

    @Query("SELECT SUM(d.quantity) FROM Donation d WHERE d.user = ?1")
    Integer getUserSumOfQuantity(User user);
}
