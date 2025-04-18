// package rmit.edu.vn.hcmc_metro.generator;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
// import rmit.edu.vn.hcmc_metro.Passenger.Passenger;
// import rmit.edu.vn.hcmc_metro.Passenger.PassengerService;
// import rmit.edu.vn.hcmc_metro.userauth.UserModel;
// import rmit.edu.vn.hcmc_metro.userauth.UserService;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// @Component
// @Order(2) // Ensures this runs second
// public class PassengerGenerator implements CommandLineRunner {

//     @Autowired
//     private PassengerService passengerService;

//     @Autowired
//     private UserService userService;

//     private List<Passenger> generatePassengersFromUsers(List<UserModel> savedUsers) {
//         List<Passenger> passengers = new ArrayList<>();

//         passengers.add(new Passenger(
//             savedUsers.get(0).getId(),
//             "John",
//             "M.",
//             "Doe",
//             "123456789",
//             LocalDate.of(1990, 1, 15),
//             "123 Main St, Cityville",
//             "1234567890",
//             "S12345",
//             false,
//             false
//         ));

//         passengers.add(new Passenger(
//             savedUsers.get(1).getId(),
//             "Jane",
//             "A.",
//             "Smith",
//             "987654321",
//             LocalDate.of(1985, 5, 20),
//             "456 Elm St, Townsville",
//             "0987654321",
//             null,
//             true,
//             false
//         ));

//         passengers.add(new Passenger(
//             savedUsers.get(2).getId(),
//             "Alice",
//             null,
//             "Brown",
//             "112233445",
//             LocalDate.of(2000, 3, 10),
//             "789 Oak St, Villagetown",
//             "1122334455",
//             "S67890",
//             false,
//             true
//         ));

//         return passengers;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         List<UserModel> savedUsers = userService.getAllUsers(); // Fetch users created by UserGenerator
//         List<Passenger> passengers = generatePassengersFromUsers(savedUsers);
//         passengerService.addPassengersFirst(passengers);
//         System.out.println("Passengers added to the repository:");
//         passengers.forEach(passenger -> System.out.println(" - " + passenger));
//     }
// }