package rmit.edu.vn.hcmc_metro.ticket_cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketCartService {

    @Autowired
    private TicketCartRepository ticketCartRepository;

    // Get a ticket cart by ID
    public Optional<TicketCart> getTicketCartById(String id) {
        return ticketCartRepository.findById(id);
    }

    // Get a ticket cart by userId
    public Optional<TicketCart> getTicketCartByUserId(String userId) {
        return ticketCartRepository.findByUserId(userId);
    }

    // Create a new ticket cart
    public TicketCart createTicketCart(TicketCart ticketCart) {
        return ticketCartRepository.save(ticketCart);
    }

    // Update an existing ticket cart
    public TicketCart updateTicketCart(String id, TicketCart updatedTicketCart) {
        if (ticketCartRepository.existsById(id)) {
            updatedTicketCart.setId(id);
            return ticketCartRepository.save(updatedTicketCart);
        } else {
            throw new RuntimeException("TicketCart with ID " + id + " not found.");
        }
    }

    // Delete a ticket cart by ID
    public void deleteTicketCart(String id) {
        if (ticketCartRepository.existsById(id)) {
            ticketCartRepository.deleteById(id);
        } else {
            throw new RuntimeException("TicketCart with ID " + id + " not found.");
        }
    }
}
