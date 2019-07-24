package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.coderslab.charity.authentication_model.Role;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.service.authentication.UserService;
import pl.coderslab.charity.service.authentication.UserServiceImpl;

import java.util.HashSet;

@SpringBootApplication
public class CharityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CharityApplication.class, args);
    }

}
