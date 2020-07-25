package pl.endproject.offerscomparator.subdomains.userRegistration.service;

public interface UserService {
    boolean isUserValid(String login, String email, String password);
    boolean registerUser(String login, String email, String password, String path);
    boolean activateUser(String token);
    long getUserId(String loginOrEmail);
}
