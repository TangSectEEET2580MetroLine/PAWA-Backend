package rmit.edu.vn.hcmc_metro.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rmit.edu.vn.hcmc_metro.metro_line.MetroLine;
import rmit.edu.vn.hcmc_metro.metro_line.MetroLineService;
import rmit.edu.vn.hcmc_metro.wallet.WalletService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private MetroLineService metroLineService;

    // Get all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Get a ticket by ID
    public Optional<Ticket> getTicketById(String id) {
        return ticketRepository.findById(id);
    }

    // Create a new ticket (admin use)
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // Update an existing ticket (admin use)
    public Ticket updateTicket(String id, Ticket updatedTicket) {
        if (ticketRepository.existsById(id)) {
            updatedTicket.setId(id);
            return ticketRepository.save(updatedTicket);
        }
        throw new RuntimeException("Ticket with ID " + id + " not found.");
    }

    // Delete a ticket by ID (admin use)
    public void deleteTicket(String id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return;
        }
        throw new RuntimeException("Ticket with ID " + id + " not found.");
    }

    /**
     * Purchase a ticket on a specific metro line.
     */
    public void purchaseTicket(PurchaseTicketRequestDTO request) {
        // 1) Load the specific line requested
        MetroLine line = metroLineService.getAllLines().stream()
                .filter(l -> l.getId().equals(request.getMetroLineId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Line not found: " + request.getMetroLineId())
                );

        // 2) Compute number of stations on that line
        List<String> stops = line.getStations();
        int i = stops.indexOf(request.getDepartureStation());
        int j = stops.indexOf(request.getArrivalStation());
        if (i < 0 || j < 0) {
            throw new RuntimeException(
                    "Stations must be on line " + line.getName() +
                            ". Got from=" + request.getDepartureStation() +
                            ", to=" + request.getArrivalStation()
            );
        }
        int numStations = Math.abs(j - i);

        // 3) Determine price
        double price;
        switch (request.getTicketType()) {
            case "ONE_WAY":
                if (numStations <= 4) {
                    price = 8_000;
                } else if (numStations <= 8) {
                    price = 12_000;
                } else {
                    price = 20_000;
                }
                break;
            case "DAILY":
                price = 40_000;
                break;
            case "THREE_DAY":
                price = 90_000;
                break;
            case "MONTHLY_STUDENT":
                price = 150_000;
                break;
            case "MONTHLY_ADULT":
                price = 300_000;
                break;
            case "FREE":
                price = 0;
                break;
            default:
                throw new RuntimeException(
                        "Invalid ticket type specified: " + request.getTicketType()
                );
        }

        // 4) Deduct from wallet if needed
        if (price > 0) {
            walletService.deductBalance(request.getPassengerId(), price);
        }

        // 5) Map to your ticketTypeId
        String ticketTypeId;
        switch (request.getTicketType()) {
            case "ONE_WAY":         ticketTypeId = "ticket_type_id_one_way"; break;
            case "DAILY":           ticketTypeId = "ticket_type_id_daily"; break;
            case "THREE_DAY":       ticketTypeId = "ticket_type_id_three_day"; break;
            case "MONTHLY_STUDENT": ticketTypeId = "ticket_type_id_monthly_student"; break;
            case "MONTHLY_ADULT":   ticketTypeId = "ticket_type_id_monthly_adult"; break;
            case "FREE":            ticketTypeId = "ticket_type_id_free"; break;
            default:
                throw new RuntimeException(
                        "TicketType ID not mapped for: " + request.getTicketType()
                );
        }

        // 6) Build & save the Ticket
        Ticket ticket = new Ticket();
        ticket.setUserId(request.getPassengerId());
        ticket.setMetroLineId(request.getMetroLineId());          // record line
        ticket.setTicketTypeId(ticketTypeId);
        ticket.setStatus(TicketStatus.INACTIVE);
        ticket.setDepartureStation(request.getDepartureStation());
        ticket.setArrivalStation(request.getArrivalStation());
        ticket.setNumberOfStations(numStations);
        ticket.setPrice(price);
        ticket.setIssueDate(LocalDateTime.now());
        ticket.setExpiryDate(determineExpiry(request.getTicketType()));

        ticketRepository.save(ticket);
    }

    // Helper to get all tickets for a user
    public List<Ticket> getTicketsByUserId(String userId) {
        return ticketRepository.findByUserId(userId);
    }

    // Expiry logic unchanged
    private LocalDateTime determineExpiry(String ticketType) {
        LocalDateTime now = LocalDateTime.now();
        switch (ticketType) {
            case "ONE_WAY":
            case "DAILY":
                return now.plusHours(24);
            case "THREE_DAY":
                return now.plusHours(72);
            case "MONTHLY_STUDENT":
            case "MONTHLY_ADULT":
                return now.plusDays(30);
            case "FREE":
                return now.plusYears(100);
            default:
                throw new IllegalArgumentException(
                        "Unknown ticket type for expiry: " + ticketType
                );
        }
    }
}
