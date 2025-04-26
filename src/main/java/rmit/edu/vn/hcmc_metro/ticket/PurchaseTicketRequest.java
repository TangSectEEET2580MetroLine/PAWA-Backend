package rmit.edu.vn.hcmc_metro.ticket;

public class PurchaseTicketRequest {
    private String passengerId;
    private String ticketType; // example: "ONE_WAY", "DAILY", "THREE_DAY", "MONTHLY_STUDENT", "MONTHLY_ADULT", "FREE"
    private int numberOfStations; // only needed for ONE_WAY ticket

    // Getters and Setters
    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public void setNumberOfStations(int numberOfStations) {
        this.numberOfStations = numberOfStations;
    }
}
