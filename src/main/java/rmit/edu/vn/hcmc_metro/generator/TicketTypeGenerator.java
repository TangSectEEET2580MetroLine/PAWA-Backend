// package rmit.edu.vn.hcmc_metro.generator;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
// import rmit.edu.vn.hcmc_metro.ticket_type.TicketType;
// import rmit.edu.vn.hcmc_metro.ticket_type.TicketTypeName;
// import rmit.edu.vn.hcmc_metro.ticket_type.TicketTypeService;

// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;

// @Component
// @Order(4) // Ensures this runs after other generators
// public class TicketTypeGenerator implements CommandLineRunner {

//     @Autowired
//     private TicketTypeService ticketTypeService;

//     private List<TicketType> generateTicketTypes() {
//         List<TicketType> ticketTypes = new ArrayList<>();

//         // One-way Ticket
//         ticketTypes.add(new TicketType(
//                 TicketTypeName.ONE_WAY.name(),
//                 0.0, // Price is dynamic based on stations
//                 new Date(), // Availability starts now
//                 "24-hour availability, Specific Stations",
//                 8_000, // Max value for up to 4 stations
//                 20_000 // Max value for more than 8 stations
//         ));

//         // Daily Ticket
//         ticketTypes.add(new TicketType(
//                 TicketTypeName.DAILY.name(),
//                 40_000,
//                 new Date(),
//                 "24-hour availability, Go anywhere",
//                 0,
//                 0
//         ));

//         // Three-day Ticket
//         ticketTypes.add(new TicketType(
//                 TicketTypeName.THREE_DAY.name(),
//                 90_000,
//                 new Date(),
//                 "72 hours after activation",
//                 0,
//                 0
//         ));

//         // Monthly Ticket for Students
//         ticketTypes.add(new TicketType(
//                 TicketTypeName.MONTHLY_STUDENT.name(),
//                 150_000,
//                 new Date(),
//                 "30 days after activation, Student ID required",
//                 0,
//                 0
//         ));

//         // Monthly Ticket for Adults
//         ticketTypes.add(new TicketType(
//                 TicketTypeName.MONTHLY_ADULT.name(),
//                 300_000,
//                 new Date(),
//                 "30 days after activation",
//                 0,
//                 0
//         ));

//         // Free Tickets
//         ticketTypes.add(new TicketType(
//                 TicketTypeName.FREE.name(),
//                 0.0,
//                 new Date(),
//                 "Eligibility: Age >= 60 || Age <= 6, Disability Status = true || Revolutionary contribution status = true",
//                 0,
//                 0
//         ));

//         return ticketTypes;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         List<TicketType> ticketTypes = generateTicketTypes();
//         ticketTypeService.createTicketType(ticketTypes);
//         System.out.println("Ticket types added to the repository:");
//         ticketTypes.forEach(System.out::println);
//     }
// }