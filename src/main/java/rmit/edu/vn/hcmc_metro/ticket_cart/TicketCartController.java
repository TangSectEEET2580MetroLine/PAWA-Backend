package rmit.edu.vn.hcmc_metro.ticket_cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/ticket-carts")
public class TicketCartController {

    @Autowired
    private TicketCartService ticketCartService;

    // Get a ticket cart by ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketCart> getTicketCartById(@PathVariable String id) {
        Optional<TicketCart> ticketCart = ticketCartService.getTicketCartById(id);
        return ticketCart.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get a ticket cart by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<TicketCart> getTicketCartByUserId(@PathVariable String userId) {
        Optional<TicketCart> ticketCart = ticketCartService.getTicketCartByUserId(userId);
        return ticketCart.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new ticket cart
    @PostMapping
    public ResponseEntity<TicketCart> createTicketCart(@RequestBody TicketCart ticketCart) {
        TicketCart createdTicketCart = ticketCartService.createTicketCart(ticketCart);
        return new ResponseEntity<>(createdTicketCart, HttpStatus.CREATED);
    }

    // Update an existing ticket cart
    @PutMapping("/{id}")
    public ResponseEntity<TicketCart> updateTicketCart(
            @PathVariable String id,
            @RequestBody TicketCart updatedTicketCart) {
        try {
            TicketCart ticketCart = ticketCartService.updateTicketCart(id, updatedTicketCart);
            return new ResponseEntity<>(ticketCart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a ticket cart by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketCart(@PathVariable String id) {
        try {
            ticketCartService.deleteTicketCart(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
