package rmit.edu.vn.hcmc_metro.Passenger;

public record ProfileUpdateRequest(
    String email,
    String residenceAddress,
    String phoneNumber,
    String password
) {}
