package rmit.edu.vn.hcmc_metro.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    // Get all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Get a ticket by ID
    public Optional<Ticket> getTicketById(String id) {
        return ticketRepository.findById(id);
    }

    // Create a new ticket
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // Update an existing ticket
    public Ticket updateTicket(String id, Ticket updatedTicket) {
        if (ticketRepository.existsById(id)) {
            updatedTicket.setId(id);
            return ticketRepository.save(updatedTicket);
        } else {
            throw new RuntimeException("Ticket with ID " + id + " not found.");
        }
    }

    // Delete a ticket by ID
    public void deleteTicket(String id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
        } else {
            throw new RuntimeException("Ticket with ID " + id + " not found.");
        }
    }
}
