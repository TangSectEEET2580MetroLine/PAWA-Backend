package rmit.edu.vn.hcmc_metro.ticket;


import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    // Additional query methods can be added here if needed


    // ADD this custom query
    Optional<Ticket> findByTicketTypeId(String ticketTypeId);
    List<Ticket> findByUserId(String userId);
}
