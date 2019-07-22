package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
