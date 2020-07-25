package pl.endproject.offerscomparator.subdomains.userRegistration.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TextValidatorTest {


    TextValidator textValidator = new TextValidator();


    @Test
    public void shouldValidateUserLoginBeforeAddUserToDB() {
        //then
        for (TestCase caseElement : testCases()
        ) {
            Assert.assertEquals(caseElement.loginResult, textValidator.loginValidate(caseElement.plainText));
        }
    }

    @Test
    public void shouldValidateUserEmailBeforeAddUserToDB() {
        //then
        for (TestCase caseElement : testCases()
        ) {
            Assert.assertEquals(caseElement.emailResult, textValidator.emailValidate(caseElement.plainText));
        }
    }

    private List<TestCase> testCases() {
        return Arrays.asList(
                new TestCase("newtestuser", true,false),
                new TestCase("newtest0user", true,false),
                new TestCase("newtest?user", false,false),
                new TestCase("newtes.tuser", true,false),
                new TestCase("newtest user", false,false),
                new TestCase("newtest @!user", false,false),
                new TestCase("newtest@!user", false,false),
                new TestCase("newtest_user", true,false),
                new TestCase("newtest_user01", true,false),
                new TestCase("616513", true,false),
                new TestCase("półka", false,false),
                new TestCase("größer", false,false),
                new TestCase("test/", false,false),
                new TestCase("test\\", false,false),
                new TestCase("test*", false,false),
                new TestCase("test@", false,false),
                new TestCase("test@ssf.com", false,true),
                new TestCase("test@ss f.com", false,false),
                new TestCase("@om", false,false),
                new TestCase("@m", false,false),
                new TestCase("bm", false,false),
                new TestCase("bsm", true,false),
                new TestCase("test@ssf.com?", false,false));
    }


    private static class TestCase {
        private String plainText;
        private Boolean loginResult;
        private Boolean emailResult;

        public TestCase(String plainText, Boolean loginResult,Boolean emailResult) {
            this.plainText = plainText;
            this.loginResult = loginResult;
            this.emailResult = emailResult;
        }
    }
}