package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.entity.Institution;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    @Query("SELECT COUNT(DISTINCT d.institution) FROM Donation d")
    Integer getInstitutionDonatedCount();
    @Query("SELECT COUNT(DISTINCT d.institution) FROM Donation d WHERE d.user = ?1")
    Integer getUserInstitutionDoantedCount(User user);
}
