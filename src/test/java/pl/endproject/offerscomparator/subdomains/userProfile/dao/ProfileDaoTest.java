package pl.endproject.offerscomparator.subdomains.userProfile.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.endproject.offerscomparator.subdomains.userProfile.model.Address;
import pl.endproject.offerscomparator.subdomains.userProfile.model.BasicInformation;
import pl.endproject.offerscomparator.subdomains.userProfile.model.Profile;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProfileDaoTest {

    @Autowired
    private ProfileDao profileDao;

    @Test
    public void findProfileByEmail() {
        //given
        String mail = "gadzinski@test.pl";
        //then
        assertThat(profileDao.findProfileByEmail(mail),hasProperty("role",is("user")));
    }

    @Test
    public void shouldReturnTrueAfterIsGivenExistingMail() {
        //given
        String mail = "gadzinski@test.pl";
        //then
        assertThat(profileDao.existsByEmail(mail),equalTo(true));
    }

    @Test
    public void shouldReturnFalseAfterIsGivenNoExistingMail() {
        //given
        String mail = "noexist@test.pl";
        //then
        assertThat(profileDao.existsByEmail(mail),equalTo(false));
    }

    @Test
    public void shouldGetListOfProfiles() {
        //then
        assertThat(profileDao.findAll().size(),is(greaterThan(0)) );
    }
    @Test
    public void shouldCreateNewProfile() {
        //given
        String mail = "dev1@test.pl";
        String login = "dev1";
        String role = "user";
        //when
        profileDao.save(new Profile(mail,login,role,new BasicInformation(),new Address()));
        System.out.println(profileDao.findAll());
        //then
        assertThat(profileDao.existsByEmail(mail),is(true));
    }

    @Test
    public void shouldUpdateProfile() {
        //given
        String mail = "dev1@test.pl";
        String address = "Mielno 23";

        //when
        Profile profile = profileDao.findProfileByEmail(mail);
        profile.getAddress().setAddress1(address);
        profileDao.save(profile);
        System.out.println(profileDao.findAll());
        //then
        assertThat(profileDao.findProfileByEmail(mail).getAddress().getAddress1(),is(equalTo(address)));
    }

    @Test
    public void shouldDeleteProfile() {
        //given
        String mail = "dev1@test.pl";


        //when
        profileDao.delete(profileDao.findProfileByEmail(mail));
        //then
        assertThat(profileDao.existsByEmail(mail),is(false));
    }
}