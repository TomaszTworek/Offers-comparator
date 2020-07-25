package pl.endproject.offerscomparator.subdomains.userRegistration.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import pl.endproject.offerscomparator.subdomains.userRegistration.configuration.MailYandexProperties;
import pl.endproject.offerscomparator.subdomains.userRegistration.dao.UserDao;
import pl.endproject.offerscomparator.subdomains.userRegistration.model.User;
import pl.endproject.offerscomparator.subdomains.userRegistration.util.EmailUtil;
import pl.endproject.offerscomparator.subdomains.userRegistration.util.PasswordUtil;
import pl.endproject.offerscomparator.subdomains.userRegistration.util.TextValidator;

import javax.mail.Session;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private User user;
    private Session switchMailSource = MailYandexProperties.config();
    private String failureCause;

    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
    }

    public void setSwitchMailSource(Session switchMailSource) {
        this.switchMailSource = switchMailSource;
    }


    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public boolean isUserValid(String login, String email, String password) {
        if (checkIfEmailAndLoginAreNotNull(login, email)) {
            this.user = userDao.findUserByLoginOrEmail(login.toLowerCase(), email.toLowerCase());
            return user != null && PasswordUtil.checkPassword(password, user.getPassword()) && user.getActive();
        }
        return false;
    }

    private boolean checkIfEmailAndLoginAreNotNull(String login, String email) {
        return login != null && email != null;
    }


    @Override
    public boolean registerUser(String login, String email, String password, String path) {
        if (checkIfEmailAndLoginAreNotNull(login, email)) {
            String token = uniqueTokenGenerator();
            String newLogin = login.toLowerCase();
            String newEmail = email.toLowerCase();
            TextValidator textValidator = new TextValidator();

            try {
                if (textValidator.loginValidate(newLogin) & textValidator.emailValidate(newEmail)) {
                    if (userDao.findUserByLoginOrEmail(newLogin, newEmail) == null) {
                        userDao.save(new User(newLogin, PasswordUtil.hashPassword(password), newEmail, token));
                        EmailUtil.sendActivationEmail(newEmail, token, path, switchMailSource);
                        return true;
                    } else {
                        if (userDao.existsByLogin(newLogin)) {
                            setFailureCause("loginExistInDB");
                        } else {
                            setFailureCause("emailExistInDB");
                        }
                        return false;
                    }
                }
                setFailureCause("illegalCharactersUsed");
                return false;

            } catch (IncorrectResultSizeDataAccessException e) {
                setFailureCause("loginAndEmailExistInDB");
                return false;
            }
        }
        return false;

    }

    private String uniqueTokenGenerator() {
        int tokenLength = 50;
        String uniqueToken;
        do {
            uniqueToken = RandomStringUtils.randomAlphanumeric(tokenLength);
        } while (userDao.findUserByToken(uniqueToken) != null);
        return uniqueToken;
    }

    @Override
    public boolean activateUser(String token) {
        this.user = userDao.findUserByToken(token);
        if (user != null && !user.getActive()) {

            user.setActive(true);
            userDao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public long getUserId(String loginOrEmail) {
        return userDao.findUserByLoginOrEmail(loginOrEmail, loginOrEmail).getUser_id();
    }


    public List<User> getUserList() {
        return userDao.findAll();
    }


    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    public void delete(User user) {
        userDao.delete(user);
    }


    public User loginUser(String usernameFromInput, String passwordFromInput) {
        User foundUser = null;
        if (isUserValid(usernameFromInput, usernameFromInput, passwordFromInput)) {
            foundUser = userDao.findUserByLoginOrEmail(usernameFromInput, usernameFromInput);
        }else{
            setFailureCause("User doesn't exist");
        }
        return foundUser;
    }
}
