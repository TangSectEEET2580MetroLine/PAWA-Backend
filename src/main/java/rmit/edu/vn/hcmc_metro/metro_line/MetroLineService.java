package rmit.edu.vn.hcmc_metro.metro_line;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
