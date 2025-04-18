package rmit.edu.vn.hcmc_metro.ticket_cart;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ticket_carts") // Specifies the MongoDB collection name
public class TicketCart {

    @Id
    private String id; // Unique cart ID
    private String userId; // Reference to the User who owns the cart
    private List<String> ticketIds; // List of ticket IDs in the cart

    // Constructors
    public TicketCart() {
    }

    public TicketCart(String userId, List<String> ticketIds) {
        this.userId = userId;
        this.ticketIds = ticketIds;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<String> ticketIds) {
        this.ticketIds = ticketIds;
    }

    @Override
    public String toString() {
        return "TicketCart{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", ticketIds=" + ticketIds +
                '}';
    }
}
