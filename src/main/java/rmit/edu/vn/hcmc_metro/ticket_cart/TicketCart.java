package rmit.edu.vn.hcmc_metro.ticket_cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ticket_carts")
public class TicketCart {

    @Id
    private String id;

    private String userId;

    private List<TicketCartItem> items = new ArrayList<>();

    // Constructors
    public TicketCart() {
    }

    public TicketCart(String userId) {
        this.userId = userId;
    }

    public List<TicketCartItem> getItems() {
        return items;
    }

    public void setItems(List<TicketCartItem> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return items.stream().mapToInt(TicketCartItem::getSubtotal).sum();
    }

    // Other getters/setters
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
}
