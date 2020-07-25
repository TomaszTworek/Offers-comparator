package pl.endproject.offerscomparator.subdomains.userRegistration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.endproject.offerscomparator.subdomains.userRegistration.model.User;

public interface UserDao extends JpaRepository<User,Long> {

    User findUserByLoginOrEmail(String login, String email);
    User findUserByLoginAndPasswordOrEmailAndPassword(String login, String password, String email, String password2);
    User findUserByToken(String token);
    Boolean existsByLogin(String login);

    User findUserByLogin(String login);
    User findUserByEmail(String email);

}
