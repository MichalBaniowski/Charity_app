package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.ActivationCode;
import pl.coderslab.charity.entity.authentication.User;

@Repository
public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {
    ActivationCode findByUser(User user);
}
