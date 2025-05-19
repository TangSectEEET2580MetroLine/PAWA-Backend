package rmit.edu.vn.hcmc_metro.Passenger;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PassengerRepository extends MongoRepository<Passenger, String> {
    Passenger findByNationalId(String nationalId);
    Optional<Passenger> findByUserId(String userId);
}
