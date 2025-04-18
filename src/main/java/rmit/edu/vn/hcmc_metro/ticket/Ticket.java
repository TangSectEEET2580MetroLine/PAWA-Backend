package rmit.edu.vn.hcmc_metro.ticket;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "tickets") // Specifies the MongoDB collection name
public class Ticket {

    @Id
    private String id; // MongoDB will auto-generate this ID
    private String ticketTypeId; // Reference to the TicketType
    private String userId; // Reference to the User who owns the ticket
    private String metroDepartureId; // Reference to the departure metro station
    private String metroArrivalId; // Reference to the arrival metro station
    private TicketStatus status; // Status of the ticket (ACTIVE, INACTIVE, EXPIRED)
    private Date issueDate; // Date when the ticket was issued
    private Date expiryDate; // Date when the ticket expires

    // Constructors
    public Ticket() {
    }

    public Ticket(String ticketTypeId, String userId, String metroDepartureId, String metroArrivalId, TicketStatus status, Date issueDate, Date expiryDate) {
        this.ticketTypeId = ticketTypeId;
        this.userId = userId;
        this.metroDepartureId = metroDepartureId;
        this.metroArrivalId = metroArrivalId;
        this.status = status;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMetroDepartureId() {
        return metroDepartureId;
    }

    public void setMetroDepartureId(String metroDepartureId) {
        this.metroDepartureId = metroDepartureId;
    }

    public String getMetroArrivalId() {
        return metroArrivalId;
    }

    public void setMetroArrivalId(String metroArrivalId) {
        this.metroArrivalId = metroArrivalId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", ticketTypeId='" + ticketTypeId + '\'' +
                ", userId='" + userId + '\'' +
                ", metroDepartureId='" + metroDepartureId + '\'' +
                ", metroArrivalId='" + metroArrivalId + '\'' +
                ", status=" + status +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
