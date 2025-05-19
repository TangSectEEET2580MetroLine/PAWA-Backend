package rmit.edu.vn.hcmc_metro.Passenger;

import java.time.LocalDate;

public record ProfileResponse(
    String userId,
    String email,
    String firstName,
    String middleName,
    String lastName,
    String nationalId,
    LocalDate dateOfBirth,
    String residenceAddress,
    String phoneNumber,
    String studentId,
    boolean disabilityStatus,
    boolean revolutionaryContributionStatus,
    double walletBalance

) {}
    
