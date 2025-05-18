package rmit.edu.vn.hcmc_metro.metro_line;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rmit.edu.vn.hcmc_metro.metro_line.DTO.TripDTO;

@RestController
@RequestMapping("/metro-line")
@CrossOrigin(origins = "http://localhost:3000")
public class MetroLineController {

    @Autowired
    private MetroLineService metroLineService;

    // Get all metro lines
    @GetMapping("/all")
    public List<MetroLine> getAllMetroLines() {
        return metroLineService.getAllLines();
    }

    // Get metro line information by ID
    @GetMapping("/{metroLineId}")
    public ResponseEntity<String> getMetroLineById(@PathVariable String metroLineId) {
        String lineInfo = metroLineService.getLineInfo(metroLineId);
        if (lineInfo.contains("not found")) {
            return new ResponseEntity<>(lineInfo, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lineInfo, HttpStatus.OK);
    }

    // Add a new metro line
    @PostMapping("/add")
    public ResponseEntity<String> addMetroLine(@RequestBody MetroLine metroLine) {
        try {
            metroLineService.addMetroLine(metroLine);
            return new ResponseEntity<>("Metro line added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add metro line: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a metro line by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMetroLine(@PathVariable String id) {
        try {
            metroLineService.deleteMetroLine(id);
            return new ResponseEntity<>("Metro line deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete metro line: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Edit an existing metro line
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editMetroLine(@PathVariable String id, @RequestBody MetroLine updatedMetroLine) {
        try {
            MetroLine existingMetroLine = metroLineService.getMetroLineById(id);
            if (existingMetroLine == null) {
                return new ResponseEntity<>("Metro line not found", HttpStatus.NOT_FOUND);
            }

            // Update the existing metro line with new values
            existingMetroLine.setName(updatedMetroLine.getName());
            existingMetroLine.setStations(updatedMetroLine.getStations());
            existingMetroLine.setDurationInMin(updatedMetroLine.getDurationInMin());

            metroLineService.addMetroLine(existingMetroLine); // Save the updated metro line
            return new ResponseEntity<>("Metro line updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update metro line: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{lineId}/trips")
    public ResponseEntity<List<TripDTO>> getTrips(
            @PathVariable String lineId,
            @RequestParam String fromStation,
            @RequestParam String toStation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime after,
            @RequestParam(defaultValue = "3") int count
    ) {
        List<TripDTO> trips = metroLineService.getNextTrips(
                lineId, fromStation, toStation, after, count
        );
        return ResponseEntity.ok(trips);
    }
}
