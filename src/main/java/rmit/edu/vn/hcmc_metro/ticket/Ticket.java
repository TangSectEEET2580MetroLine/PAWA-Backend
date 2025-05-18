package rmit.edu.vn.hcmc_metro.ticket;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;

    // Reference to your TicketType config
    private String ticketTypeId;

    // The passenger who owns this ticket
    private String userId;
    // Metro line ID for line station
    private String metroLineId;

    // Human-readable station names
    private String departureStation;
    private String arrivalStation;

    // Computed distance in number of stops
    private int numberOfStations;

    // Fare charged
    private double price;

    private TicketStatus status;

    // When the ticket was issued
    private LocalDateTime issueDate;

    // When it expires
    private LocalDateTime expiryDate;

    public Ticket() { }

    public Ticket(String ticketTypeId,
                  String userId,
                  String metroLineId,
                  String departureStation,
                  String arrivalStation,
                  int numberOfStations,
                  double price,
                  TicketStatus status,
                  LocalDateTime issueDate,
                  LocalDateTime expiryDate) {
        this.ticketTypeId      = ticketTypeId;
        this.userId            = userId;
        this.metroLineId       = metroLineId;
        this.departureStation  = departureStation;
        this.arrivalStation    = arrivalStation;
        this.numberOfStations  = numberOfStations;
        this.price             = price;
        this.status            = status;
        this.issueDate         = issueDate;
        this.expiryDate        = expiryDate;
    }

    // ─── Getters & Setters ─────────────────────────────────────────────────────────

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMetroLineId() {
        return metroLineId;
    }
    public void setMetroLineId(String metroLineId) {
        this.metroLineId = metroLineId;
    }

    public String getTicketTypeId() {
        return ticketTypeId;
    }
    public void setTicketTypeId(String ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartureStation() {
        return departureStation;
    }
    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }
    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }
    public void setNumberOfStations(int numberOfStations) {
        this.numberOfStations = numberOfStations;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public TicketStatus getStatus() {
        return status;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", ticketTypeId='" + ticketTypeId + '\'' +
                ", userId='" + userId + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", arrivalStation='" + arrivalStation + '\'' +
                ", numberOfStations=" + numberOfStations +
                ", price=" + price +
                ", status=" + status +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
