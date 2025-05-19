package rmit.edu.vn.hcmc_metro.ticket;



public class PurchaseTicketRequestDTO {
    private String passengerId;
    private String ticketType; // example: "ONE_WAY", "DAILY", "THREE_DAY", "MONTHLY_STUDENT", "MONTHLY_ADULT", "FREE"
    private String metroLineId;            // new!
    private String departureStation;
    private String arrivalStation;
    private int numberOfStations; // only needed for ONE_WAY ticket

    // Getters and Setters
    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
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

    public String getMetroLineId() {
        return metroLineId;
    }
    public void setMetroLineId(String metroLineId) {
        this.metroLineId = metroLineId;
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
