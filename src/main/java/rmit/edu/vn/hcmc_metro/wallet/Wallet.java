package rmit.edu.vn.hcmc_metro.wallet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wallets") // Specifies the MongoDB collection name
public class Wallet {

    @Id
    private String id; // MongoDB will auto-generate this ID

    @Indexed(unique = true)
    private String userId; // Reference to the User who owns the wallet
    private double balance; // Current balance in the wallet

    // Constructors
    public Wallet() {
    }

    public Wallet(String userId, double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
