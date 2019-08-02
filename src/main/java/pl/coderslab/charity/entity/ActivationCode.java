package pl.coderslab.charity.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.charity.entity.authentication.User;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "activation_codes")
public class ActivationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    String code;

    public ActivationCode(User user, String code) {
        this.user = user;
        this.code = code;
    }
}
