package pl.endproject.offerscomparator.subdomains.userRegistration.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.endproject.offerscomparator.OffersComparatorApplication;
import pl.endproject.offerscomparator.subdomains.userRegistration.config.H2TestProfileJPAConfig;
import pl.endproject.offerscomparator.subdomains.userRegistration.configuration.MailTrapProperties;
import pl.endproject.offerscomparator.subdomains.userRegistration.configuration.MailYandexProperties;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        OffersComparatorApplication.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        userService.setSwitchMailSource(MailTrapProperties.config());
    }

    @After
    public void tearDown() {
        userService.setSwitchMailSource(MailYandexProperties.config());
    }

    @Test
    public void isUserValid() {
        //then
        for (TestCase singleTest : testCasesSelectedParametersAreCovered()) {
            assertThat(userService.isUserValid(singleTest.login, singleTest.email, singleTest.password1)).isEqualTo(singleTest.result);
        }
    }

    @Test
    public void shouldNotRegisterUserWhileInvalidParametersAreGiven() {
        String login = "marta";
        String email = "j@test.pl";
        String password = "testPassword";
        String path = "http://localhost:8080/signup";

        //then
        for (TestCase singleTest : testCasesRegistrationFailed()) {
            userService.registerUser(singleTest.login, singleTest.email, "randomPassword", path);
            assertThat(userService.getFailureCause()).isEqualTo(singleTest.registrationStatus);
        }
    }

    @Test
    public void activateUser() {
        //given
        String success = "testtokentesttokentesttoken1";
        String failed = "testtokentesttokentesttoken2";

        //when
        Boolean actualFirst = userService.activateUser(success);
        Boolean actualSecond = userService.activateUser(failed);

        //then
        assertThat(actualFirst).isTrue();
        assertThat(actualSecond).isFalse();
        assertThat(userService.findById(1).get().getActive()).isTrue();
    }

    @Test
    public void getUserId() {
    }

    @Test
    public void getUserList() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void delete() {
    }

    private List<TestCase> testCasesSelectedParametersAreCovered() {
        return Arrays.asList(
                new TestCase("Marek", "Jan", "Marek", true),
                new TestCase("Marek", "Nowak", "mj@test.pl", false),
                new TestCase(null, "Jan", null, false),
                new TestCase("Marek", "Jan", "mj@test.pl", true),
                new TestCase("Marek", "Jan", "Marek", true),
                new TestCase("mj@test.pl", "Jan", "mj@test.pl", true),
                new TestCase("Marek", "Jan", null, false),
                new TestCase(null, "Jan", null, false),
                new TestCase("Jan", "Jan", null, false),
                new TestCase("M arek", "Jan", null, false),
                new TestCase("", "Jan", "", false),
                new TestCase(null, "Janek", "mj@test.pl", false)
        );
    }

    private List<TestCase> testCasesRegistrationFailed() {
        return Arrays.asList(
                new TestCase("Marek", "mj@test.pl", "loginExistInDB"),
                new TestCase("Marek", "difmj@test.pl", "loginExistInDB"),
                new TestCase("RandomName", "mj@test.pl", "emailExistInDB"),
                new TestCase("Anna", "mj@test.pl", "loginAndEmailExistInDB"),
                new TestCase("Marek", "difm!j@test.pl", "illegalCharactersUsed"),
                new TestCase("Mar ek", "difmj@test.pl", "illegalCharactersUsed"),
                new TestCase("", "", "illegalCharactersUsed")
        );
    }


    private static class TestCase {
        private String login;
        private String password1;
        private String email;
        private Boolean result;
        private String registrationStatus;


        public TestCase(String login, String password1, String email, Boolean result) {
            this.login = login;
            this.password1 = password1;
            this.email = email;
            this.result = result;
        }

        public TestCase(String login, String email, String registrationStatus) {
            this.login = login;
            this.email = email;
            this.registrationStatus = registrationStatus;
        }
    }
}