package pl.endproject.offerscomparator.subdomains.userRegistration.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.endproject.offerscomparator.subdomains.userRegistration.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
//@SpringBootTest //allow modify true db
@DataJpaTest // embedded in-memory database. Fake database will disable full auto-configuration and instead apply only configuration relevant to JPA tests.
//@ActiveProfiles("test") //only required to tests h2 db
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoMySqlTest {

    @Autowired
    private UserDao userDao;


    @Test
    public void shouldGetInitializeUsersAfterConnectingTheMySqlDB() {

        //then
        assertThat(userDao.findById(1L).get().getLogin()).isEqualTo("remotemysql");
    }

    @Test
    public void shouldAddUserToDBWhileFourParametersAreGiven() {
        //given
        User testUser = new User("NewTestUser", "NewTesterPassword", "test@email.com", "1qazxsw2");
        //when
        userDao.save(testUser);
        //then
        assertThat(userDao.findUserByLoginOrEmail("NewTestUser",null).getLogin()).isEqualTo("NewTestUser");
    }

    /**
     * When @SpringBootTest is used, change the parameter.
     * Running the test will permanently remove the element from the database.
     * */
    @Test
    public void remove() {
        //given
        User testUser = userDao.findUserByLoginOrEmail("remotemysql",null);
        //when
        userDao.delete(testUser);
        //then
        assertThat(userDao.findUserByLoginOrEmail("remotemysql",null)).isNull();
    }
}