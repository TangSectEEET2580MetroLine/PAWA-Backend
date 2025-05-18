package rmit.edu.vn.hcmc_metro.metro_line;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;

@Document(collection = "metro_lines") // Specify the MongoDB collection name
public class MetroLine {

  @Id
  private String id; // MongoDB uses String IDs by default

  @Indexed(unique = true)
  private String name;

  private List<String> stations; // No need for @ElementCollection in MongoDB

  private int durationInMin;

  // Habanero II.PA.3 schedule fields
  private LocalTime firstDepartureTime;   // e.g., 05:30
  private int frequencyInMinutes;

  public MetroLine() {
  }

  public MetroLine(String name, List<String> stations, int durationInMin, LocalTime firstDepartureTime, int frequencyInMinutes) {
    this.name = name;
    this.stations = stations;
    this.durationInMin = durationInMin;
    this.firstDepartureTime = firstDepartureTime;
    this.frequencyInMinutes = frequencyInMinutes;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }

  public List<String> getStations() {
    return stations;
  }

  public int getDurationInMin() {
    return durationInMin;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStations(List<String> stations) {
    this.stations = stations;
  }

  public void setDurationInMin(int durationInMin) {
    this.durationInMin = durationInMin;
  }

  public LocalTime getFirstDepartureTime() { return firstDepartureTime; }
  public void setFirstDepartureTime(LocalTime t) { this.firstDepartureTime = t; }

  public int getFrequencyInMinutes() { return frequencyInMinutes; }
  public void setFrequencyInMinutes(int freq) { this.frequencyInMinutes = freq; }

  @Override
  public String toString() {
    return "MetroLine [id=" + id + ", name=" + name + ", stations=" + stations + ", durationInMin=" + durationInMin + "]";
  }
}
