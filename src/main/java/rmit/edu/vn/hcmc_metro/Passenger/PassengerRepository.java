package rmit.edu.vn.hcmc_metro.Passenger;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PassengerRepository extends MongoRepository<Passenger, String> {
    Passenger findByNationalId(String nationalId);
    Passenger findByUserId(String userId);
}
