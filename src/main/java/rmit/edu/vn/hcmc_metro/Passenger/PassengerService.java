package rmit.edu.vn.hcmc_metro.Passenger;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import rmit.edu.vn.hcmc_metro.wallet.Wallet;
import rmit.edu.vn.hcmc_metro.wallet.WalletRepository;

import rmit.edu.vn.hcmc_metro.userauth.UserModel;
import rmit.edu.vn.hcmc_metro.userauth.UserRepository;

import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final WalletRepository    walletRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository,
                            WalletRepository walletRepository, UserRepository userRepository, BCryptPasswordEncoder encoder ) {
        this.passengerRepository = passengerRepository;
        this.walletRepository    = walletRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    //Read the profile
    public ProfileResponse getProfile(String userId) {
        Passenger p = passengerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        UserModel u = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        double balance = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"))
                .getBalance();

        return new ProfileResponse(
                userId, u.getEmail(), p.getFirstName(), p.getMiddleName(), p.getLastName(),
                p.getNationalId(), p.getDateOfBirth(), p.getResidenceAddress(),
                p.getPhoneNumber(), p.getStudentId(), p.isDisabilityStatus(),
                p.isRevolutionaryContributionStatus(), balance
        );
    }

    //Update the profile
    public ProfileResponse updateProfile(String userId, ProfileUpdateRequest req) {
        Passenger p = passengerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        if (req.residenceAddress() != null) {
            p.setResidenceAddress(req.residenceAddress());
        }
        if (req.phoneNumber() != null) {
            p.setPhoneNumber(req.phoneNumber());
        }
        passengerRepository.save(p);

        UserModel u = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.email() != null) {
            u.setEmail(req.email());
        }
        if (req.password() != null) {
            u.setPassword(encoder.encode(req.password()));
        }
        userRepository.save(u);

        double balance = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"))
                .getBalance();

        return new ProfileResponse(
                userId, u.getEmail(), p.getFirstName(), p.getMiddleName(), p.getLastName(),
                p.getNationalId(), p.getDateOfBirth(), p.getResidenceAddress(),
                p.getPhoneNumber(), p.getStudentId(), p.isDisabilityStatus(),
                p.isRevolutionaryContributionStatus(), balance
        );
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

    /** Upload front/back ID image for passenger */
    public void uploadIdImages(String passengerId, MultipartFile front, MultipartFile back) throws IOException {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(passengerId);
        if (optionalPassenger.isEmpty()) {
            throw new RuntimeException("Passenger not found");
        }

        validateImage(front, "front");
        validateImage(back, "back");

        String uploadDir = "uploads/passenger_ids";
        new File(uploadDir).mkdirs();

        String frontFilename = passengerId + "_front." + getFileExtension(front);
        String backFilename = passengerId + "_back." + getFileExtension(back);

        Path frontPath = Paths.get(uploadDir, frontFilename);
        Path backPath = Paths.get(uploadDir, backFilename);

        Files.write(frontPath, front.getBytes());
        Files.write(backPath, back.getBytes());

        Passenger passenger = optionalPassenger.get();
        passenger.setFrontIdImageUrl(frontPath.toString());
        passenger.setBackIdImageUrl(backPath.toString());

        passengerRepository.save(passenger);
    }

    // Service: Add new method updateIdImages() with validation
    public void updateIdImages(String passengerId, MultipartFile front, MultipartFile back) throws IOException {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(passengerId);
        if (optionalPassenger.isEmpty()) {
            throw new RuntimeException("Passenger not found");
        }

        validateImage(front, "front");
        validateImage(back, "back");

        String uploadDir = "uploads/passenger_ids";
        new File(uploadDir).mkdirs();

        // Build file names
        String frontFilename = passengerId + "_front." + getFileExtension(front);
        String backFilename = passengerId + "_back." + getFileExtension(back);

        Path frontPath = Paths.get(uploadDir, frontFilename);
        Path backPath = Paths.get(uploadDir, backFilename);

        // Delete old if exists
        Files.deleteIfExists(frontPath);
        Files.deleteIfExists(backPath);

        // Write new files
        Files.write(frontPath, front.getBytes());
        Files.write(backPath, back.getBytes());

        Passenger passenger = optionalPassenger.get();
        passenger.setFrontIdImageUrl(frontPath.toString());
        passenger.setBackIdImageUrl(backPath.toString());

        passengerRepository.save(passenger);
    }

    private void validateImage(MultipartFile file, String label) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException(label + " image is missing");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException(label + " image exceeds 5MB");
        }
        String type = file.getContentType();
        if (type == null || !(type.equals("image/jpeg") || type.equals("image/png") || type.equals("image/jpg"))) {
            throw new RuntimeException(label + " image must be PNG or JPG");
        }
    }

    private String getFileExtension(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.contains(".")) {
            throw new RuntimeException("Invalid file name");
        }
        return originalName.substring(originalName.lastIndexOf('.') + 1);
    }

    /*private void validateImage(MultipartFile file, String fieldName) {
        if (file.isEmpty()) throw new RuntimeException(fieldName + " image is empty");
        if (file.getSize() > 5 * 1024 * 1024) throw new RuntimeException(fieldName + " image exceeds 5MB");

        String contentType = file.getContentType();
        if (!List.of("image/jpeg", "image/png").contains(contentType)) {
            throw new RuntimeException(fieldName + " image must be JPEG or PNG");
        }
    }

    private String getFileExtension(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf('.') + 1);
    }*/
}
