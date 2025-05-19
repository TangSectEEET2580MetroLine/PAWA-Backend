package rmit.edu.vn.hcmc_metro.ticket_cart;

import rmit.edu.vn.hcmc_metro.ticket_type.TicketType;

public class TicketCartItem {

    private String id;

    private TicketType ticketType;

    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSubtotal() {
        return (int) (ticketType.getPrice() * quantity);
    }
}
