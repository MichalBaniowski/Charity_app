package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.ActivationCode;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.repository.ActivationCodeRepository;
import org.apache.commons.lang3.RandomStringUtils;

@Service
public class ActivationService {

    private final String LOCAL_HOST_ADDRESS = "http://localhost:8080";

    @Autowired
    private ActivationCodeRepository activationCodeRepository;

    public boolean checkCode(String code, User user) {
        ActivationCode activationCode = activationCodeRepository.findByUser(user);
        boolean result = activationCode.getCode().equals(code);
        if(result) {
            activationCodeRepository.delete(activationCode);
        }
        return result;
    }

    public String getActivationLink(User user) {
        ActivationCode activationCode = activationCodeRepository.findByUser(user);
        String code = generateActivationCode();
        if(activationCode != null) {
            activationCode.setCode(code);
            updateCode(activationCode);
        } else {
            saveCodeToDb(user, code);
        }
        return String.format("%s/activation?userId=%d&code=%s",
                LOCAL_HOST_ADDRESS, user.getId(), code);
    }

    private void saveCodeToDb(User user, String code) {
        activationCodeRepository.save(new ActivationCode(user, code));
    }

    private void updateCode(ActivationCode activationCode) {
        activationCodeRepository.save(activationCode);
    }

    private String generateActivationCode() {
        int length = 20;
        return RandomStringUtils.random(length, true, false);
    }

}
