package rmit.edu.vn.hcmc_metro.ticket_cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.edu.vn.hcmc_metro.ticket_type.TicketType;
import rmit.edu.vn.hcmc_metro.ticket_type.TicketTypeRepository;

import java.util.Optional;

@Service
public class TicketCartService {

    @Autowired
    private TicketCartRepository ticketCartRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    // ----------------------------- EXISTING CRUD -----------------------------

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

    // ----------------------------- II.PA.5 FEATURES -----------------------------

    public TicketCart addTicket(String userId, String ticketTypeId, int quantity) {
        TicketCart cart = ticketCartRepository.findByUserId(userId)
                .orElse(new TicketCart(userId));

        TicketType type = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new RuntimeException("Invalid ticket type"));

        Optional<TicketCartItem> existing = cart.getItems().stream()
                .filter(i -> i.getTicketType().getId().equals(ticketTypeId))
                .findFirst();

        if (existing.isPresent()) {
            TicketCartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            TicketCartItem newItem = new TicketCartItem();
            newItem.setTicketType(type);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        return ticketCartRepository.save(cart);
    }

    public TicketCart removeTicket(String userId, String ticketItemId) {
        TicketCart cart = ticketCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getId().equals(ticketItemId));
        return ticketCartRepository.save(cart);
    }

    public TicketCart clearCart(String userId) {
        TicketCart cart = ticketCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().clear();
        return ticketCartRepository.save(cart);
    }
}
