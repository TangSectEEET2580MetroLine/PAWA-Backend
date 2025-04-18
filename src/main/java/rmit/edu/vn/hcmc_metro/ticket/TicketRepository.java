package rmit.edu.vn.hcmc_metro.ticket;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    // Additional query methods can be added here if needed
}
