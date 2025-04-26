package rmit.edu.vn.hcmc_metro.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rmit.edu.vn.hcmc_metro.wallet.WalletService;
import rmit.edu.vn.hcmc_metro.wallet.Wallet;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private WalletService walletService; // Inject WalletService


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

    public void purchaseTicket(PurchaseTicketRequest request) {
        // 1. Calculate ticket price based on type and number of stations
        double price = 0.0;
        switch (request.getTicketType()) {
            case "ONE_WAY":
                if (request.getNumberOfStations() <= 4) {
                    price = 8000;
                } else if (request.getNumberOfStations() <= 8) {
                    price = 12000;
                } else {
                    price = 20000;
                }
                break;
            case "DAILY":
                price = 40000;
                break;
            case "THREE_DAY":
                price = 90000;
                break;
            case "MONTHLY_STUDENT":
                price = 150000;
                break;
            case "MONTHLY_ADULT":
                price = 300000;
                break;
            case "FREE":
                price = 0;
                break;
            default:
                throw new RuntimeException("Invalid ticket type specified: " + request.getTicketType());
        }

        // 2. Deduct wallet if price > 0
        if (price > 0) {
            walletService.deductBalance(request.getPassengerId(), price);
        }

        // 3. Hardcoded TicketTypeId mapping (Temporary solution)
        String ticketTypeId;
        switch (request.getTicketType()) {
            case "ONE_WAY":
                ticketTypeId = "ticket_type_id_one_way"; // Replace with actual ID if known
                break;
            case "DAILY":
                ticketTypeId = "ticket_type_id_daily";
                break;
            case "THREE_DAY":
                ticketTypeId = "ticket_type_id_three_day";
                break;
            case "MONTHLY_STUDENT":
                ticketTypeId = "ticket_type_id_monthly_student";
                break;
            case "MONTHLY_ADULT":
                ticketTypeId = "ticket_type_id_monthly_adult";
                break;
            case "FREE":
                ticketTypeId = "ticket_type_id_free";
                break;
            default:
                throw new RuntimeException("TicketType ID not mapped for: " + request.getTicketType());
        }

        // 4. Create and save the Ticket
        Ticket ticket = new Ticket();
        ticket.setUserId(request.getPassengerId()); // PassengerId is stored in userId
        ticket.setTicketTypeId(ticketTypeId); // Use the mapped TicketTypeId
        ticket.setStatus(TicketStatus.INACTIVE); // New ticket is INACTIVE by default
        ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByUserId(String userId) {
        return ticketRepository.findByUserId(userId);
    }


}
