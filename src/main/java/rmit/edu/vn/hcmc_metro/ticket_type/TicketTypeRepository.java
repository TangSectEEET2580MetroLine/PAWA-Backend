package rmit.edu.vn.hcmc_metro.ticket_type;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends MongoRepository<TicketType, String> {
}
