package pl.coderslab.charity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.authentication_model.User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Min(0)
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
    @NotNull
    private User user;
    @Size(min = 1)
    private String street;
    @Size(min = 1)
    private String city;
    @Size(min = 1)
    private String zipCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Size(min = 1)
    private LocalDate pickUpDate;
    @DateTimeFormat(pattern = "HH:mm")
    @Size(min = 1)
    private LocalTime pickUpTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;
    @Size(max = 250)
    private String pickUpComment;
    @Size(min = 1)
    private String contactPhone;
    private boolean status;

    @PrePersist
    void prePersistSetCreated() {
        created = LocalDateTime.now();
        status = false;
    }

}
