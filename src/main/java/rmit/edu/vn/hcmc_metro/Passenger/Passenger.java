package rmit.edu.vn.hcmc_metro.Passenger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "passengers") // Specify the MongoDB collection name
public class Passenger {

    @Id
    private String id; // MongoDB uses String IDs by default

    @Indexed(unique = true)
    private String userId; // New unique user ID for Passenger

    private String firstName;

    private String middleName;

    private String lastName;

    @Indexed(unique = true)
    private String nationalId; // Ensure nationalId is unique

    private LocalDate dateOfBirth;

    private String residenceAddress;

    private String phoneNumber;

    private String studentId;

    private boolean disabilityStatus;

    private boolean revolutionaryContributionStatus;

    private String frontIdImageUrl;

    private String backIdImageUrl;

    public Passenger() {
    }

    public Passenger(String userId, String firstName, String middleName, String lastName, String nationalId,
                     LocalDate dateOfBirth, String residenceAddress, String phoneNumber, String studentId,
                     boolean disabilityStatus, boolean revolutionaryContributionStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.dateOfBirth = dateOfBirth;
        this.residenceAddress = residenceAddress;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
        this.disabilityStatus = disabilityStatus;
        this.revolutionaryContributionStatus = revolutionaryContributionStatus;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean isDisabilityStatus() {
        return disabilityStatus;
    }

    public void setDisabilityStatus(boolean disabilityStatus) {
        this.disabilityStatus = disabilityStatus;
    }

    public boolean isRevolutionaryContributionStatus() {
        return revolutionaryContributionStatus;
    }

    public void setRevolutionaryContributionStatus(boolean revolutionaryContributionStatus) {
        this.revolutionaryContributionStatus = revolutionaryContributionStatus;
    }

    public String getFrontIdImageUrl() {
        return frontIdImageUrl;
    }

    public void setFrontIdImageUrl(String frontIdImageUrl) {
        this.frontIdImageUrl = frontIdImageUrl;
    }

    public String getBackIdImageUrl() {
        return backIdImageUrl;
    }

    public void setBackIdImageUrl(String backIdImageUrl) {
        this.backIdImageUrl = backIdImageUrl;
    }

    @Override
    public String toString() {
        return "Passenger [id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", middleName=" + middleName +
                ", lastName=" + lastName + ", nationalId=" + nationalId + ", dateOfBirth=" + dateOfBirth +
                ", residenceAddress=" + residenceAddress + ", phoneNumber=" + phoneNumber + ", studentId=" + studentId +
                ", disabilityStatus=" + disabilityStatus + ", revolutionaryContributionStatus=" + revolutionaryContributionStatus + "]";
    }
}
