package rmit.edu.vn.hcmc_metro.wallet;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
    // Find a wallet by userId
    Optional<Wallet> findByUserId(String userId);
}
