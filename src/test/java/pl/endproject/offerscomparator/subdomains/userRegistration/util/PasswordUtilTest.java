package pl.endproject.offerscomparator.subdomains.userRegistration.util;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PasswordUtilTest {


    @Test
    public void shouldHashPasswordWhenPlainTextIsGiven() {
        //given
        String password_plaintext="gadzinski";

        //when
        String actual = PasswordUtil.hashPassword(password_plaintext);

        //then
        assertThat(actual,is(startsWith("$2a$")));
        assertThat(actual.length(),allOf((greaterThan(0)),(lessThan(250))));
    }

    @Test
    public void shouldReturnTrueWhenSamePasswordIsVerified() {
        //given
        String password_plaintext="Jan";
        String hashPassword = "$2a$12$.mmDJgNVKpog5tA9NgLYSOgEafJ7Bv83rtkK7BI/vh4tdyvObGmH2";
        //when
        Boolean actual = PasswordUtil.checkPassword(password_plaintext,hashPassword);
        //then
        assertThat(actual,is(true));
    }
    @Test

    public void shouldReturnFalseWhenDifferentPasswordIsVerified() {
        //given
        String password_plaintext="InnyNowak";
        String hashPassword = "$2a$12$4kqOmPr2uOBrqVAETBb0BOPejzEHinA5itgsDZyI39yKrNPiPLwri";
        //when
        Boolean actual = PasswordUtil.checkPassword(password_plaintext,hashPassword);
        //then
        assertThat(actual,is(false));
    }
}