package rmit.edu.vn.hcmc_metro.ticket_type;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "ticket_types") // Specifies the MongoDB collection name
public class TicketType {

    @Id
    private String id; // MongoDB will auto-generate this ID
    private String name; // Name of the ticket type
    private double price; // Price of the ticket
    private Date availability; // Availability as a Date
    private String requisite; // e.g., "Student ID required"
    private int maxValue; // Maximum value or limit
    private int minValue; // Minimum value or limit

    // Constructors
    public TicketType() {
    }

    public TicketType(String name, double price, Date availability, String requisite, int maxValue, int minValue) {
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.requisite = requisite;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getAvailability() {
        return availability;
    }

    public void setAvailability(Date availability) {
        this.availability = availability;
    }

    public String getRequisite() {
        return requisite;
    }

    public void setRequisite(String requisite) {
        this.requisite = requisite;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    @Override
    public String toString() {
        return "TicketType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                ", requisite='" + requisite + '\'' +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                '}';
    }
}
