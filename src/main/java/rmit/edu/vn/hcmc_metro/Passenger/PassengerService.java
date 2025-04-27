package rmit.edu.vn.hcmc_metro.Passenger;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rmit.edu.vn.hcmc_metro.wallet.WalletRepository;
import rmit.edu.vn.hcmc_metro.wallet.Wallet;

@Component
public class PassengerService {

    private final WalletRepository walletRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    PassengerService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassengerById(String passengerId) {
        return passengerRepository.findById(passengerId).orElse(null);
    }

    public void addPassenger(Passenger passenger) {
        passengerRepository.save(passenger);
        walletRepository.findByUserId(passenger.getUserId())
            .orElseGet(() -> walletRepository.save(new Wallet(passenger.getUserId(), 0)));
    }

    public void addPassengers(List<Passenger> passengers) {
        for (Passenger p: passengers) {
            addPassenger(p);
        }
    }

    // Use for testing
    public void addPassengersFirst(List<Passenger> passengers) {
        for (Passenger passenger : passengers) {
            // Check if a passenger with the same nationalId already exists
            Passenger existingPassenger = passengerRepository.findByNationalId(passenger.getNationalId());
            if (existingPassenger != null) {
                // Update the existing passenger
                existingPassenger.setFirstName(passenger.getFirstName());
                existingPassenger.setMiddleName(passenger.getMiddleName());
                existingPassenger.setLastName(passenger.getLastName());
                existingPassenger.setDateOfBirth(passenger.getDateOfBirth());
                existingPassenger.setResidenceAddress(passenger.getResidenceAddress());
                existingPassenger.setPhoneNumber(passenger.getPhoneNumber());
                existingPassenger.setStudentId(passenger.getStudentId());
                existingPassenger.setDisabilityStatus(passenger.isDisabilityStatus());
                existingPassenger.setRevolutionaryContributionStatus(passenger.isRevolutionaryContributionStatus());
                passengerRepository.save(existingPassenger); // Save the updated passenger
            } else {
                // Add a new passenger if no matching nationalId exists
                passengerRepository.save(passenger);
            }
            walletRepository.findByUserId(passenger.getUserId())
                .orElseGet(() -> walletRepository.save(new Wallet(passenger.getUserId(), 0)));
        }
    }

    public void deletePassenger(String passengerId) {
        passengerRepository.deleteById(passengerId);
    }

    public Passenger editPassenger(String passengerId, Passenger updatedPassenger) {
        Passenger existingPassenger = passengerRepository.findById(passengerId).orElse(null);
        if (existingPassenger != null) {
            // Update the existing passenger with new values
            existingPassenger.setFirstName(updatedPassenger.getFirstName());
            existingPassenger.setMiddleName(updatedPassenger.getMiddleName());
            existingPassenger.setLastName(updatedPassenger.getLastName());
            existingPassenger.setNationalId(updatedPassenger.getNationalId());
            existingPassenger.setDateOfBirth(updatedPassenger.getDateOfBirth());
            existingPassenger.setResidenceAddress(updatedPassenger.getResidenceAddress());
            existingPassenger.setPhoneNumber(updatedPassenger.getPhoneNumber());
            existingPassenger.setStudentId(updatedPassenger.getStudentId());
            existingPassenger.setDisabilityStatus(updatedPassenger.isDisabilityStatus());
            existingPassenger.setRevolutionaryContributionStatus(updatedPassenger.isRevolutionaryContributionStatus());

            return passengerRepository.save(existingPassenger); // Save and return the updated passenger
        }
        return null; // Return null if the passenger does not exist
    }
}