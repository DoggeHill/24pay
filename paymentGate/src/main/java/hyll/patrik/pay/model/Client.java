package hyll.patrik.pay.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Client object POJO
 *
 * @author Patrik
 */
public class Client {

    // Fields
    @NotBlank(message = "ClientId may not be blank")
    @Size(min = 3, max = 10)
    private String clientId;

    @NotBlank(message = "FirstName may not be blank")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "FamilyName may not be blank")
    @Size(min = 2, max = 50)
    private String familyName;

    @NotBlank(message = "Email may not be blank")
    @Size(min = 6, max = 128)
    private String email;

    @NotBlank(message = "Country may not be blank")
    private String country;

    // Standard getters and setters

    public String getClientId() {
        return clientId;
    }

    @Autowired
    public void setClientId(@Value("") String clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
