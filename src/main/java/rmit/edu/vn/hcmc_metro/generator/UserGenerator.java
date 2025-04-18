// package rmit.edu.vn.hcmc_metro.generator;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Component;
// import rmit.edu.vn.hcmc_metro.userauth.UserModel;
// import rmit.edu.vn.hcmc_metro.userauth.UserService;

// import java.util.ArrayList;
// import java.util.List;

// @Component
// @Order(1) // Ensures this runs first
// public class UserGenerator implements CommandLineRunner {

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private BCryptPasswordEncoder passwordEncoder;

//     private List<UserModel> generateUsers() {
//         List<UserModel> users = new ArrayList<>();

//         users.add(new UserModel(
//             "john.doe@example.com",
//             passwordEncoder.encode("password123"),
//             "ADMIN",
//             true
//         ));

//         users.add(new UserModel(
//             "jane.smith@example.com",
//             passwordEncoder.encode("securepass"),
//             "ADMIN",
//             true
//         ));

//         users.add(new UserModel(
//             "alice.brown@example.com",
//             passwordEncoder.encode("mypassword"),
//             "ADMIN",
//             true
//         ));

//         return users;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         List<UserModel> users = generateUsers();
//         List<UserModel> savedUsers = userService.createUserFirst(users);
//         System.out.println("Users added to the repository:");
//         savedUsers.forEach(System.out::println);
//     }
// }
