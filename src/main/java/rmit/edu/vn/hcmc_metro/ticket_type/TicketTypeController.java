package rmit.edu.vn.hcmc_metro.ticket_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ticket-types")
public class TicketTypeController {

    @Autowired
    private TicketTypeService ticketTypeService;

    // Get all ticket types
    @GetMapping
    public ResponseEntity<List<TicketType>> getAllTicketTypes() {
        List<TicketType> ticketTypes = ticketTypeService.getAllTicketTypes();
        return new ResponseEntity<>(ticketTypes, HttpStatus.OK);
    }

    // Get a ticket type by ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketType> getTicketTypeById(@PathVariable String id) {
        Optional<TicketType> ticketType = ticketTypeService.getTicketTypeById(id);
        return ticketType.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new ticket type
    @PostMapping
    public ResponseEntity<TicketType> createTicketType(@RequestBody TicketType ticketType) {
        TicketType createdTicketType = ticketTypeService.createTicketType(ticketType);
        return new ResponseEntity<>(createdTicketType, HttpStatus.CREATED);
    }

    // Update an existing ticket type
    @PutMapping("/{id}")
    public ResponseEntity<TicketType> updateTicketType(
            @PathVariable String id,
            @RequestBody TicketType updatedTicketType) {
        try {
            TicketType ticketType = ticketTypeService.updateTicketType(id, updatedTicketType);
            return new ResponseEntity<>(ticketType, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a ticket type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketType(@PathVariable String id) {
        try {
            ticketTypeService.deleteTicketType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
