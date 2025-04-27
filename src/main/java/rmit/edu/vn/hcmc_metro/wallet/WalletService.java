package rmit.edu.vn.hcmc_metro.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    // Get a wallet by ID
    public Optional<Wallet> getWalletById(String id) {
        return walletRepository.findById(id);
    }

    // Get a wallet by userId
    public Optional<Wallet> getWalletByUserId(String userId) {
        return walletRepository.findByUserId(userId);
    }

    // Create a new wallet
    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    // Update an existing wallet
    public Wallet updateWallet(String id, Wallet updatedWallet) {
        if (walletRepository.existsById(id)) {
            updatedWallet.setId(id);
            return walletRepository.save(updatedWallet);
        } else {
            throw new RuntimeException("Wallet with ID " + id + " not found.");
        }
    }

    // Delete a wallet by ID
    public void deleteWallet(String id) {
        if (walletRepository.existsById(id)) {
            walletRepository.deleteById(id);
        } else {
            throw new RuntimeException("Wallet with ID " + id + " not found.");
        }
    }

    // Add balance to a wallet
    public Wallet addBalance(String userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet for userId " + userId + " not found."));
        wallet.setBalance(wallet.getBalance() + amount);
        return walletRepository.save(wallet);
    }

    // Deduct balance from a wallet
    public Wallet deductBalance(String userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet for userId " + userId + " not found."));
        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in wallet.");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        return walletRepository.save(wallet);
    }

    //Top up a wallet
    @Transactional
    public Wallet topUp(String userId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("You have entered a negative top-up amount. Please try again with a positive value.");

        Wallet wallet = walletRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Wallet for userId " + userId + " not found."));
        
        wallet.setBalance(wallet.getBalance() + amount);
        return walletRepository.save(wallet);
    }

    //Get a wallet's balance
    public double getBalance(String userId) {
        return walletRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet for userId " + userId + " not found."))
            .getBalance();
    }
}
