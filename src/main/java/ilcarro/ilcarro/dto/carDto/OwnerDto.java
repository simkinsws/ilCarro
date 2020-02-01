package ilcarro.ilcarro.dto.carDto;

import java.util.Date;

public class OwnerDto {
    private String firstName;
    private String secondName;
    private Date registrationDate;

    public OwnerDto(String firstName,
                    String secondName,
                    Date registrationDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.registrationDate = registrationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public OwnerDto() {
    }

    @Override
    public String toString() {
        return "OwnerDto{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
