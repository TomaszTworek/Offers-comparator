package pl.endproject.offerscomparator.subdomains.userProfile.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.endproject.offerscomparator.subdomains.userProfile.dao.ProfileDao;

@RunWith(SpringRunner.class)
@SpringBootTest //allow modify true db
//@DataMongoTest
//@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class ProfileServiceImplTest {

    @Autowired
    private ProfileDao profileDao;

    @Test
    public void shouldFindUserByEmailWhenEmailIsGiven() {
        String email="gadzinski@test.pl";

        System.out.println(profileDao.findProfileByEmail(email));
    }


}