package rmit.edu.vn.hcmc_metro.ticket_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketTypeService {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    // Get all ticket types
    public List<TicketType> getAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }

    // Get a ticket type by ID
    public Optional<TicketType> getTicketTypeById(String id) {
        return ticketTypeRepository.findById(id);
    }

    // Create a new ticket type
    public TicketType createTicketType(TicketType ticketType) {
        return ticketTypeRepository.save(ticketType);
    }

    // Create multiple ticket types
    public List<TicketType> createTicketType(List<TicketType> ticketTypes) {
        return ticketTypeRepository.saveAll(ticketTypes);
    }

    // Update an existing ticket type
    public TicketType updateTicketType(String id, TicketType updatedTicketType) {
        if (ticketTypeRepository.existsById(id)) {
            updatedTicketType.setId(id);
            return ticketTypeRepository.save(updatedTicketType);
        } else {
            throw new RuntimeException("TicketType with ID " + id + " not found.");
        }
    }

    // Delete a ticket type by ID
    public void deleteTicketType(String id) {
        if (ticketTypeRepository.existsById(id)) {
            ticketTypeRepository.deleteById(id);
        } else {
            throw new RuntimeException("TicketType with ID " + id + " not found.");
        }
    }
}