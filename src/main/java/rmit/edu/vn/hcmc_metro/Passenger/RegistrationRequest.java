package rmit.edu.vn.hcmc_metro.Passenger;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class RegistrationRequest {

    @NotBlank(message="Email cannot be blank")
    @Email(message="Must be a valid email address")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|vn)$",
            message = "Email must be valid and end with .com or .vn (e.g., example@domain.com)"
    )
    private String email;

    @NotBlank(message="Password cannot be blank")
    @Size(min=8, message="Password must be at least 8 characters")
    @Pattern(
            regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).+$",
            message="Password must contain upper, lower, digit, and special char"
    )
    private String password;

    @NotBlank(message="First name is required")
    @Pattern(
            regexp="^[\\p{L} ]+$",
            message="First name must contain only letters"
    )
    @Size(max=50, message="First name max 50 characters")
    private String firstName;

    @Pattern(
            regexp="^[\\p{L} ]*$",
            message="Middle name must contain only letters"
    )
    @Size(max=50, message="Middle name max 50 characters")
    private String middleName;

    @NotBlank(message="Last name is required")
    @Pattern(
            regexp="^[\\p{L} ]+$",
            message="Last name must contain only letters"
    )
    @Size(max=50, message="Last name max 50 characters")
    private String lastName;

    @NotBlank(message="National ID is required")
    @Pattern(regexp="\\d{12}", message="National ID must be exactly 12 digits")
    private String nationalId;

    @NotNull(message="Date of birth is required")
    @Past(message="Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message="Residence address is required")
    @Pattern(
            regexp="^[\\dA-Za-z\\s,\\.\\-\\/]+$",
            message="Address may contain letters, digits, , . - / only"
    )
    private String residenceAddress;

    @NotBlank(message="Phone number is required")
    @Pattern(
            regexp="^0\\d{9}$",
            message="Phone must be 10 digits, starting with 0 (e.g., 0921123456)"
    )
    private String phoneNumber;

    @Pattern(
            regexp="^[A-Za-z0-9]{0,15}$",
            message="Student ID max 15 alphanumeric characters"
    )
    private String studentId;  // optional

    @NotNull(message="Disability status must be specified")
    private Boolean disabilityStatus;

    @NotNull(message="Revolutionary status must be specified")
    private Boolean revolutionaryContributionStatus;

    // ─── Getters & Setters ─────────────────────────────────────────────────────────

    public String getEmail()                   { return email; }
    public void setEmail(String email)         { this.email = email; }

    public String getPassword()                { return password; }
    public void setPassword(String password)   { this.password = password; }

    public String getFirstName()               { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName()               { return middleName; }
    public void setMiddleName(String middleName){ this.middleName = middleName; }

    public String getLastName()                { return lastName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }

    public String getNationalId()              { return nationalId; }
    public void setNationalId(String nationalId){ this.nationalId = nationalId; }

    public LocalDate getDateOfBirth()          { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dob)  { this.dateOfBirth = dob; }

    public String getResidenceAddress()        { return residenceAddress; }
    public void setResidenceAddress(String addr){ this.residenceAddress = addr; }

    public String getPhoneNumber()             { return phoneNumber; }
    public void setPhoneNumber(String phone)   { this.phoneNumber = phone; }

    public String getStudentId()               { return studentId; }
    public void setStudentId(String sid)       { this.studentId = sid; }

    public Boolean getDisabilityStatus()       { return disabilityStatus; }
    public void setDisabilityStatus(Boolean d) { this.disabilityStatus = d; }

    public Boolean getRevolutionaryContributionStatus() {
        return revolutionaryContributionStatus;
    }
    public void setRevolutionaryContributionStatus(Boolean r) {
        this.revolutionaryContributionStatus = r;
    }
}
