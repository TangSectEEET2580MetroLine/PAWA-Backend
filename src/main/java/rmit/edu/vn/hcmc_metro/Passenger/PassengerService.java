package rmit.edu.vn.hcmc_metro.Passenger;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import rmit.edu.vn.hcmc_metro.wallet.Wallet;
import rmit.edu.vn.hcmc_metro.wallet.WalletRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final WalletRepository    walletRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository,
                            WalletRepository walletRepository) {
        this.passengerRepository = passengerRepository;
        this.walletRepository    = walletRepository;
    }

    /** Fetch all passengers */
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    /** Lookup by passengerId */
    public Optional<Passenger> getPassengerById(String passengerId) {
        return passengerRepository.findById(passengerId);
    }

    /**
     * Persist a new passenger, and ensure the user has a wallet initialized at 0.
     * @return the saved Passenger
     */
    public Passenger addPassenger(Passenger passenger) {
        Passenger saved = passengerRepository.save(passenger);

        // If no wallet exists for this user, create one with 0 balance
        walletRepository.findByUserId(saved.getUserId())
                .orElseGet(() -> walletRepository.save(
                        new Wallet(saved.getUserId(), 0.0)
                ));

        return saved;
    }

    /**
     * Check whether a passenger with the given National ID is already registered.
     */
    public boolean existsByNationalId(String nationalId) {
        return passengerRepository.findByNationalId(nationalId) != null;
    }

    /** Bulk add (with wallet creation) */
    public void addPassengers(List<Passenger> passengers) {
        for (Passenger p : passengers) {
            addPassenger(p);
        }
    }

    /**
     * Bulk upsert on nationalId, then ensure wallets
     * “First‐time” style load.
     */
    public void addPassengersFirst(List<Passenger> passengers) {
        for (Passenger p : passengers) {
            Passenger existing = passengerRepository
                    .findByNationalId(p.getNationalId());
            if (existing != null) {
                // copy over updated fields
                existing.setFirstName(p.getFirstName());
                existing.setMiddleName(p.getMiddleName());
                existing.setLastName(p.getLastName());
                existing.setDateOfBirth(p.getDateOfBirth());
                existing.setResidenceAddress(p.getResidenceAddress());
                existing.setPhoneNumber(p.getPhoneNumber());
                existing.setStudentId(p.getStudentId());
                existing.setDisabilityStatus(p.isDisabilityStatus());
                existing.setRevolutionaryContributionStatus(
                        p.isRevolutionaryContributionStatus()
                );
                p = passengerRepository.save(existing);
            } else {
                p = passengerRepository.save(p);
            }
            Passenger finalP = p;
            walletRepository.findByUserId(p.getUserId())
                    .orElseGet(() -> walletRepository.save(
                            new Wallet(finalP.getUserId(), 0.0)
                    ));
        }
    }

    /** Delete by passengerId */
    public void deletePassenger(String passengerId) {
        passengerRepository.deleteById(passengerId);
    }

    /**
     * Update an existing passenger’s details.
     * @return the updated Passenger or null if not found
     */
    public Passenger editPassenger(String passengerId, Passenger updated) {
        return passengerRepository.findById(passengerId)
                .map(existing -> {
                    existing.setFirstName(updated.getFirstName());
                    existing.setMiddleName(updated.getMiddleName());
                    existing.setLastName(updated.getLastName());
                    existing.setNationalId(updated.getNationalId());
                    existing.setDateOfBirth(updated.getDateOfBirth());
                    existing.setResidenceAddress(updated.getResidenceAddress());
                    existing.setPhoneNumber(updated.getPhoneNumber());
                    existing.setStudentId(updated.getStudentId());
                    existing.setDisabilityStatus(updated.isDisabilityStatus());
                    existing.setRevolutionaryContributionStatus(
                            updated.isRevolutionaryContributionStatus()
                    );
                    return passengerRepository.save(existing);
                })
                .orElse(null);
    }

    public Passenger findPassengerByUserId(String userId) {
        return passengerRepository.findByUserId(userId);
    }

    public void deleteAll() {
        passengerRepository.deleteAll();
    }
}
