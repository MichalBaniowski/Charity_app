package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.ActivationCode;
import pl.coderslab.charity.entity.ForgotPassword;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.repository.ActivationCodeRepository;
import org.apache.commons.lang3.RandomStringUtils;
import pl.coderslab.charity.repository.ForgotPasswordRepository;

@Service
public class AccountService {

    private final String LOCAL_HOST_ADDRESS = "http://localhost:8080";

    private ActivationCodeRepository activationCodeRepository;
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    public AccountService(ActivationCodeRepository activationCodeRepository, ForgotPasswordRepository forgotPasswordRepository) {
        this.activationCodeRepository = activationCodeRepository;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

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
        String code = generateToken();
        if(activationCode != null) {
            activationCode.setCode(code);
            updateCode(activationCode);
        } else {
            saveCodeToDb(user, code);
        }
        return String.format("%s/activation?userId=%d&code=%s",
                LOCAL_HOST_ADDRESS, user.getId(), code);
    }

    public boolean checkToken(String token, User user) {
        ForgotPassword forgotPassword = forgotPasswordRepository.findByUser(user);
        boolean result = forgotPassword.getToken().equals(token);
        if(result) {
            forgotPasswordRepository.delete(forgotPassword);
        }
        return result;
    }

    public String getChangePasswordLink(User user) {
        ForgotPassword forgotPassword = forgotPasswordRepository.findByUser(user);
        String token = generateToken();
        if (forgotPassword != null) {
            forgotPassword.setToken(token);
            updateToken(forgotPassword);
        } else {
            saveForgotPasswordTokenToDb(user, token);
        }
        return String.format("%s/reset-password?userId=%d&token=%s",
                LOCAL_HOST_ADDRESS, user.getId(), token);
    }

    private void saveForgotPasswordTokenToDb(User user, String token) {
        forgotPasswordRepository.save(new ForgotPassword(user, token));
    }

    private void updateToken(ForgotPassword forgotPassword) {
        forgotPasswordRepository.save(forgotPassword);
    }

    private void saveCodeToDb(User user, String code) {
        activationCodeRepository.save(new ActivationCode(user, code));
    }

    private void updateCode(ActivationCode activationCode) {
        activationCodeRepository.save(activationCode);
    }

    private String generateToken() {
        int length = 20;
        return RandomStringUtils.random(length, true, false);
    }

}
