package rmit.edu.vn.hcmc_metro.generator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import rmit.edu.vn.hcmc_metro.Passenger.Passenger;
import rmit.edu.vn.hcmc_metro.Passenger.PassengerService;
import rmit.edu.vn.hcmc_metro.metro_line.MetroLine;
import rmit.edu.vn.hcmc_metro.metro_line.MetroLineService;
import rmit.edu.vn.hcmc_metro.userauth.UserModel;
import rmit.edu.vn.hcmc_metro.userauth.UserService;

@Component
public class DataGenerator implements CommandLineRunner {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private MetroLineService metroLineService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder

    private Map<String, ArrayList<String>> metroLinesAndStations = new HashMap<>();

    // Generate users with encoded passwords
    public List<UserModel> generateUsers() {
        List<UserModel> users = new ArrayList<>();

        users.add(new UserModel(
            "john.doe@example.com",
            passwordEncoder.encode("password123"), // Encode password
            "ADMIN",
            true
        ));

        users.add(new UserModel(
            "jane.smith@example.com",
            passwordEncoder.encode("securepass"), // Encode password
            "ADMIN",
            true
        ));

        users.add(new UserModel(
            "alice.brown@example.com",
            passwordEncoder.encode("mypassword"), // Encode password
            "ADMIN",
            true
        ));

        // Add more users as needed for testing
        return users;
    }

    // Generate passengers based on saved users
    public List<Passenger> generatePassengersFromUsers(List<UserModel> savedUsers) {
        List<Passenger> passengers = new ArrayList<>();

        // Map each Passenger to a UserModel by index or logic
        passengers.add(new Passenger(
            savedUsers.get(0).getId(), // Link userId to UserModel.id
            "John",
            "M.",
            "Doe",
            "123456789", // nationalId
            LocalDate.of(1990, 1, 15),
            "123 Main St, Cityville",
            "1234567890",
            "S12345",
            false,
            false
        ));

        passengers.add(new Passenger(
            savedUsers.get(1).getId(), // Link userId to UserModel.id
            "Jane",
            "A.",
            "Smith",
            "987654321", // nationalId
            LocalDate.of(1985, 5, 20),
            "456 Elm St, Townsville",
            "0987654321",
            null,
            true,
            false
        ));

        passengers.add(new Passenger(
            savedUsers.get(2).getId(), // Link userId to UserModel.id
            "Alice",
            null,
            "Brown",
            "112233445", // nationalId
            LocalDate.of(2000, 3, 10),
            "789 Oak St, Villagetown",
            "1122334455",
            "S67890",
            false,
            true
        ));

        // Add more passengers as needed for testing
        return passengers;
    }

    @Override
    public void run(String... args) throws Exception {
        // Generate and save users first
        List<UserModel> users = generateUsers();
        List<UserModel> savedUsers = userService.createUserFirst(users); // Save users and get their IDs
        System.out.println("Users added to the repository:");
        savedUsers.forEach(System.out::println);

        // Generate passengers based on saved users and save them
        List<Passenger> passengers = generatePassengersFromUsers(savedUsers);
        passengerService.addPassengersFirst(passengers);
        System.out.println("Passengers added to the repository:");
        passengers.forEach(passenger -> System.out.println(" - " + passenger));

        // Generate and save metro lines
        List<MetroLine> metroLines = generateMetroLines();
        metroLineService.addMetroLinesFirst(metroLines);
        System.out.println("Metro lines added to the repository:");
        metroLines.forEach(System.out::println);
    }

    // Generate metro lines
    public List<MetroLine> generateMetroLines() {
        List<MetroLine> metroLines = new ArrayList<>();

        // Define stations for Line 1
        metroLinesAndStations.put("Line 1",
            new ArrayList<>() {
                {
                    add("Ben Thanh Station");
                    add("Opera House Station");
                    add("Ba Son Station");
                    add("Van Thanh Park Station");
                    add("Tan Cang Station");
                    add("Thao Dien Station");
                    add("An Phu Station");
                    add("Rach Chiec Station");
                    add("Phuoc Long Station");
                    add("Binh Thai Station");
                    add("Thu Duc Station");
                    add("Hi-Tech Park Station");
                    add("National University Station");
                    add("Suoi Tien Terminal Station");
                }
            }
        );

        // Add Line 1 to the list
        metroLines.add(
            new MetroLine(
                "Line 1",
                metroLinesAndStations.get("Line 1"),
                30
            )
        );

        // Generate lines 2 to 11
        for (int i = 2; i <= 11; i++) {
            final int stationCode = i;
            metroLinesAndStations.put("Line " + i,
                new ArrayList<>() {
                    {
                        add(String.format("Line %d Station A", stationCode));
                        add(String.format("Line %d Station B", stationCode));
                        add(String.format("Line %d Station C", stationCode));
                        add(String.format("Line %d Station D", stationCode));
                    }
                }
            );
            metroLines.add(
                new MetroLine(
                    "Line " + i,
                    metroLinesAndStations.get("Line " + i),
                    20 + i
                )
            );
        }

        return metroLines;
    }
}