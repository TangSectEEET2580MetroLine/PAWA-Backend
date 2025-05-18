package rmit.edu.vn.hcmc_metro.metro_line.DTO;

import java.time.LocalDateTime;

public class TripDTO {
    private String lineId;
    private String fromStation;
    private String toStation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public TripDTO(String lineId, String fromStation, String toStation,
                   LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.lineId = lineId;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
    // getters
    public String getLineId() { return lineId; }
    public String getFromStation() { return fromStation; }
    public String getToStation() { return toStation; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
}