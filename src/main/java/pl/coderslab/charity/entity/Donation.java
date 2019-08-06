package pl.coderslab.charity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.validator.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Long id;
    @Min(1)
    private int quantity;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "donation_category", joinColumns = @JoinColumn(name = "donation_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    @NotEmpty
    private Set<Category> categories =new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "institution_id")
    @NotNull
    private Institution institution;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String zipCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate pickUpDate;
    @DateTimeFormat(pattern = "HH:mm")
    @NotNull
    private LocalTime pickUpTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;
    @Size(max = 250)
    private String pickUpComment;
    @NotBlank
    private String contactPhone;
    private boolean status;

    @PrePersist
    void prePersistSetCreated() {
        created = LocalDateTime.now();
    }

}
