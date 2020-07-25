package pl.endproject.offerscomparator.subdomains.userProfile.model;

import lombok.Data;

@Data
public class Address {
    private String address1;
    private String address2;
    private String cityTown;
    private String zipCode;
    private String country;
}