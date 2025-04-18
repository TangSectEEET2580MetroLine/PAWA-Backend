package rmit.edu.vn.hcmc_metro.ticket_cart;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketCartRepository extends MongoRepository<TicketCart, String> {
    // Find a ticket cart by userId
    Optional<TicketCart> findByUserId(String userId);
}
