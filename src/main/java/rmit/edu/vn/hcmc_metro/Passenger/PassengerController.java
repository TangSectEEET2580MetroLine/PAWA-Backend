package rmit.edu.vn.hcmc_metro.Passenger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "http://localhost:3000")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping("/all")
    public List<Passenger> getAllPassengers() {
        System.out.println("Fetching all passengers...");
        return passengerService.getAllPassengers();
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable String passengerId) {
        System.out.println("Fetching passenger with ID: " + passengerId);
        Passenger passenger = passengerService.getPassengerById(passengerId);
        if (passenger == null) {
            System.out.println("Passenger not found with ID: " + passengerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Passenger found: " + passenger);
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPassenger(@RequestBody Passenger passenger) {
        try {
            passengerService.addPassenger(passenger);
            System.out.println("Passenger added successfully: " + passenger);
            return new ResponseEntity<>("Passenger added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Failed to add passenger: " + e.getMessage());
            return new ResponseEntity<>("Failed to add passenger: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable String id) {
        try {
            passengerService.deletePassenger(id);
            System.out.println("Passenger deleted successfully with ID: " + id);
            return new ResponseEntity<>("Passenger deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Failed to delete passenger with ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>("Failed to delete passenger: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editPassenger(@PathVariable String id, @RequestBody Passenger updatedPassenger) {
        try {
            Passenger editedPassenger = passengerService.editPassenger(id, updatedPassenger);
            if (editedPassenger == null) {
                System.out.println("Passenger not found with ID: " + id);
                return new ResponseEntity<>("Passenger not found", HttpStatus.NOT_FOUND);
            }
            System.out.println("Passenger updated successfully: " + editedPassenger);
            return new ResponseEntity<>("Passenger updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Failed to update passenger with ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>("Failed to update passenger: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}