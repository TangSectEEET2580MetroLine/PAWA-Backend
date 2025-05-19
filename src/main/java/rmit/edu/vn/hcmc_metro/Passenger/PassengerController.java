package rmit.edu.vn.hcmc_metro.Passenger;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "http://localhost:3000")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String userId) {
        return ResponseEntity.ok(passengerService.getProfile(userId));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<ProfileResponse> updateProfile(@PathVariable String userId, @RequestBody ProfileUpdateRequest req) {
        return ResponseEntity.ok(passengerService.updateProfile(userId, req));
    }

    @GetMapping("/all")
    public List<Passenger> getAllPassengers() {
        System.out.println("Fetching all passengers...");
        return passengerService.getAllPassengers();
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<Passenger> getPassengerById(
            @PathVariable String passengerId
    ) {
        Optional<Passenger> opt = passengerService.getPassengerById(passengerId);
        return opt
                .map(passenger -> ResponseEntity.ok(passenger))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null)
                );
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

    @PostMapping("/{id}/upload-id")
    public ResponseEntity<String> uploadIdImages(
            @PathVariable String id,
            @RequestParam("front") MultipartFile front,
            @RequestParam("back") MultipartFile back) {

        try {
            passengerService.uploadIdImages(id, front, back);
            return ResponseEntity.ok("ID images uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload failed: " + e.getMessage());
        }
    }
}