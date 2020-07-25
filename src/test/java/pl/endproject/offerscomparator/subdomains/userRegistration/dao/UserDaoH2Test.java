package pl.endproject.offerscomparator.subdomains.userRegistration.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.endproject.offerscomparator.OffersComparatorApplication;
import pl.endproject.offerscomparator.subdomains.userRegistration.config.H2TestProfileJPAConfig;
import pl.endproject.offerscomparator.subdomains.userRegistration.model.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        OffersComparatorApplication.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoH2Test {


    @Autowired
    private UserDao userDao;


    @Test
    public void shouldGetInitializeUsersAfterConnectingTheH2DB() {

        //then
        assertThat(userDao.findAll().size()).isGreaterThan(0);
    }

    @Test
    public void shouldAddUserToDBAfterUserIsCreated() {
        //given
        User testUser = new User("NewTestUser", "NewTesterPassword", "test@email.com", "1qazxsw2", false, 0, LocalDate.of(2020, 5, 30), "user");
        //when
        userDao.save(testUser);
        //then
        assertThat(userDao.findById(4L).get().getLogin()).isEqualTo("NewTestUser");
    }

    @Test()
    public void shouldUpdateUserAfterIdIsGiven() {
        //given
        User testUser = new User(1L, "Alicja", "Lewandowska", "al@test.pl", "testtokentesttokentesttoken", false, 0, LocalDate.of(2020, 5, 30), "user");
        //when
        userDao.save(testUser);
        //then
        assertThat(userDao.findById(1L).get().getLogin()).isEqualTo("Alicja");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionAfterNoExistIdIsGiven() {
        //given
        Long id = 2L;
        //when
        userDao.deleteById(id);
        //then
        assertThat(userDao.findById(2L).get());
    }

    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void shouldThrowIncorrectResultSizeDataAccessExceptionWhenLoginAndEmailHasAlreadyExist() {
        //given
        String login = "anna";
        String email = "jk@test.pl";
        //when
        userDao.findUserByLoginOrEmail(login, email);
    }

    @Test
    public void shouldFindUserByLoginWhenLoginIsGiven() {
        //given
        String loginExistInDB = "anna";
        String loginDoesNotExistInDB = "Anna";

        //when
        Boolean success = userDao.existsByLogin(loginExistInDB);
        Boolean failure = userDao.existsByLogin(loginDoesNotExistInDB);

        //then
        assertThat(success).isTrue();
        assertThat(failure).isFalse();
    }


    @Test
    public void shouldFindUserByTokenAfterTokenParameterIsGiven() {
        //given
        String realToken = "testtokentesttokentesttoken3";
        String noExistToken = "blabla";
        //when
        User userExist = userDao.findUserByToken(realToken);
        User userNoExist = userDao.findUserByToken(noExistToken);
        //then
        assertThat(userExist).isNotNull();
        assertThat(userNoExist).isNull();
    }

    @Test
    public void shouldFindUserByLoginAndPasswordOrEmailAndPasswordAfterParametersAreGiven() {

        //then
        for (TestCase singleTest : testCasesAllParametersAreCovered()) {
            assertThat(userDao.findUserByLoginAndPasswordOrEmailAndPassword(singleTest.login, singleTest.password1, singleTest.email, singleTest.password2)).isEqualTo(singleTest.user);
        }
    }

    @Test
    public void shouldFindUserByLoginOrEmailAfterParametersAreGiven() {

        //then
        for (TestCase singleTest : testCasesSelectedParametersAreCovered()) {
            assertThat(userDao.findUserByLoginOrEmail(singleTest.login, singleTest.email)).isEqualTo(singleTest.user);
        }
    }

    @Test
    public void shouldReturnUniqueToken() {
        //given
        int tokenLength = 50;
        String uniqueToken;
        List<String> tokensList = userDao.findAll().stream().map(User::getToken).collect(Collectors.toList());
        //when
        do {
            uniqueToken = RandomStringUtils.randomAlphanumeric(tokenLength);
        } while (userDao.findUserByToken(uniqueToken) != null);
        //then
        assertThat(uniqueToken).isNotIn(tokensList);
        assertThat("testtokentesttokentesttoken3").isIn(tokensList);
    }

    private List<TestCase> testCasesAllParametersAreCovered() {
        return Arrays.asList(
                new TestCase("jan", null, null, null, null),
                new TestCase(null, "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", null, null, null),
                new TestCase(null, null, "jk@test.pl", null, null),
                new TestCase(null, null, null, "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", null),
                new TestCase(null, "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", null, "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", null),
                new TestCase("jan", null, "jk@test.pl", null, null),
                new TestCase(null, null, "jk@test.pl", "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", userDao.findById(1L).get()),
                new TestCase("jan", "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", null, "Nowak", userDao.findById(1L).get()),
                new TestCase(null, null, "jk@test.pl", "wrong password", null),
                new TestCase(null, null, "wrongEmail@test.pl", "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", null),
                new TestCase("jan", "wrong password", null, "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", null),
                new TestCase("wrong name", "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", null, "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri", null)
        );
    }

    private List<TestCase> testCasesSelectedParametersAreCovered() {
        return Arrays.asList(
                new TestCase("jan", null, userDao.findById(1L).get()),
                new TestCase(null, "jk@test.pl", userDao.findById(1L).get()),
                new TestCase("janek", "jk@test.pl", userDao.findById(1L).get()),
                new TestCase("jan", "jz@test.pl", userDao.findById(1L).get()),
                new TestCase("jan", "jk@test.pl", userDao.findById(1L).get()),
                new TestCase("janek", "jz@test.pl", null)
        );
    }

    private static class TestCase {
        private String login;
        private String password1;
        private String email;
        private String password2;
        private User user;

        public TestCase(String login, String password1, String email, String password2, User user) {
            this.login = login;
            this.password1 = password1;
            this.email = email;
            this.password2 = password2;
            this.user = user;
        }

        public TestCase(String login, String email, User user) {
            this.login = login;
            this.email = email;
            this.user = user;
        }
    }
}