// package rmit.edu.vn.hcmc_metro.generator;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
// import rmit.edu.vn.hcmc_metro.userauth.UserModel;
// import rmit.edu.vn.hcmc_metro.userauth.UserService;
// import rmit.edu.vn.hcmc_metro.wallet.Wallet;
// import rmit.edu.vn.hcmc_metro.wallet.WalletService;

// import java.util.ArrayList;
// import java.util.List;

// @Component
// @Order(3) // Ensures this runs third
// public class WalletGenerator implements CommandLineRunner {

//     @Autowired
//     private WalletService walletService;

//     @Autowired
//     private UserService userService;

//     private List<Wallet> generateWalletsForUsers(List<UserModel> users) {
//         List<Wallet> wallets = new ArrayList<>();
//         for (UserModel user : users) {
//             wallets.add(new Wallet(user.getId(), 100.0)); // Default balance of 100.0
//         }
//         return wallets;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         List<UserModel> users = userService.getAllUsers(); // Fetch all users created by UserGenerator
//         List<Wallet> wallets = generateWalletsForUsers(users);
//         wallets.forEach(walletService::createWallet); // Save each wallet
//         System.out.println("Wallets added to the repository:");
//         wallets.forEach(System.out::println);
//     }
// }