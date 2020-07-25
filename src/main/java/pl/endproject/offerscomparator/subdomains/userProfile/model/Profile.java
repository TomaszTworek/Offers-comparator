package pl.endproject.offerscomparator.subdomains.userProfile.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@Document(collection = "profiles")
public class Profile {
    @Id
    private String id;
    private String email;
    private String login;
    private String role;
    private BasicInformation basicInformation;
    private Address address;

    public Profile(String email, String login, String role, BasicInformation basicInformation, Address address) {
        this.email = email;
        this.login = login;
        this.role = role;
        this.basicInformation = basicInformation;
        this.address = address;
    }
}
