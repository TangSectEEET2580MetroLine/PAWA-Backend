package rmit.edu.vn.hcmc_metro.metro_line;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rmit.edu.vn.hcmc_metro.metro_line.DTO.TripDTO;

@Component
public class MetroLineService {

    @Autowired
    private MetroLineRepository metroLineRepository;

    // Get all metro lines
    public List<MetroLine> getAllLines() {
        return metroLineRepository.findAll();
    }

    // Get metro line information by ID
    public String getLineInfo(String metroLineId) {
        MetroLine metroLine = metroLineRepository.findById(metroLineId).orElse(null);
        if (metroLine == null) {
            return "Metro line not found with ID: " + metroLineId;
        }
        return metroLine.toString();
    }

    // Add a single metro line
    public void addMetroLine(MetroLine metroLine) {
        metroLineRepository.save(metroLine);
    }

    // Add multiple metro lines
    public void addMetroLines(List<MetroLine> metroLines) {
        metroLineRepository.saveAll(metroLines);
    }

    //testing only
    public void addMetroLinesFirst(List<MetroLine> metroLines) {
        for (MetroLine metroLine : metroLines) {
            // Check if a metro line with the same name already exists
            MetroLine existingMetroLine = metroLineRepository.findByName(metroLine.getName());
            if (existingMetroLine != null) {
                // Update the existing metro line
                existingMetroLine.setStations(metroLine.getStations());
                existingMetroLine.setDurationInMin(metroLine.getDurationInMin());
                existingMetroLine.setFirstDepartureTime(metroLine.getFirstDepartureTime());
                existingMetroLine.setFrequencyInMinutes(metroLine.getFrequencyInMinutes());
                metroLineRepository.save(existingMetroLine); // Save the updated metro line
            } else {
                // Add a new metro line
                metroLineRepository.save(metroLine);
            }
        }
    }
    // Get a metro line by ID
    public MetroLine getMetroLineById(String metroLineId) {
        return metroLineRepository.findById(metroLineId).orElse(null);
    }

    // Delete a metro line by ID
    public void deleteMetroLine(String metroLineId) {
        metroLineRepository.deleteById(metroLineId);
    }


    public MetroLine getLineById(String id) {
        return metroLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Line not found: " + id));
    }

    public List<TripDTO> getNextTrips(String lineId,
                                      String fromStation,
                                      String toStation,
                                      LocalDateTime after,
                                      int count) {
        // 1) Fetch & null-check
        MetroLine line = metroLineRepository.findById(lineId)
                .orElseThrow(() -> new NoSuchElementException("Line not found: " + lineId));

        Objects.requireNonNull(line.getFirstDepartureTime(),
                () -> "First departure time not configured for line " + lineId);

        // 2) Station indices
        List<String> stops = line.getStations();
        int i = stops.indexOf(fromStation);
        int j = stops.indexOf(toStation);
        if (i<0 || j<0) {
            throw new IllegalArgumentException("Station not on this line: " + fromStation + " / " + toStation);
        }

        // 3) Compute travel time
        double perSegment = (double) line.getDurationInMin() / (stops.size() - 1);
        long travelMin = Math.round(Math.abs(j - i) * perSegment);

        // 4) Find first departure after 'after'
        LocalDate date = after.toLocalDate();
        LocalTime firstTime = line.getFirstDepartureTime();
        LocalDateTime nextDep = LocalDateTime.of(date, firstTime);

        int freq = line.getFrequencyInMinutes();
        if (nextDep.isBefore(after)) {
            long minutesLate = ChronoUnit.MINUTES.between(nextDep, after);
            long cycles = minutesLate / freq + 1;
            nextDep = nextDep.plusMinutes(cycles * freq);
        }

        // 5) Build the list of TripDTO
        List<TripDTO> result = new ArrayList<>();
        for (int k = 0; k < count; k++) {
            LocalDateTime dep = nextDep.plusMinutes((long)k * freq);
            result.add(new TripDTO(
                    lineId,
                    fromStation,
                    toStation,
                    dep,
                    dep.plusMinutes(travelMin)
            ));
        }
        return result;
    }

    public void deleteAll() {
        metroLineRepository.deleteAll();
    }
}
