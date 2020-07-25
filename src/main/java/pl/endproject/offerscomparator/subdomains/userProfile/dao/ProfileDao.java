package pl.endproject.offerscomparator.subdomains.userProfile.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import pl.endproject.offerscomparator.subdomains.userProfile.model.Profile;

import java.util.List;


public interface ProfileDao extends MongoRepository<Profile, String> {
    Profile findProfileByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean deleteByEmail(String email);

    @Override
    List<Profile> findAll();
}
