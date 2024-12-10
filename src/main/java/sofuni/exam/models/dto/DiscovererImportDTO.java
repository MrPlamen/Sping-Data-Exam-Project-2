package sofuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Size;

public class DiscovererImportDTO {

    @Expose
    @Size(min = 2, max = 20)
    private String firstName;

    @Expose
    @Size(min = 2, max = 20)
    private String lastName;

    @Expose
    @Size(min = 5, max = 20)
    private String nationality;

    @Expose
    @Size(min = 5, max = 20)
    private String occupation;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
