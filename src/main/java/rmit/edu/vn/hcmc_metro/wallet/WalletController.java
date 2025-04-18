package rmit.edu.vn.hcmc_metro.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    // Get a wallet by ID
    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable String id) {
        Optional<Wallet> wallet = walletService.getWalletById(id);
        return wallet.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get a wallet by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<Wallet> getWalletByUserId(@PathVariable String userId) {
        Optional<Wallet> wallet = walletService.getWalletByUserId(userId);
        return wallet.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new wallet
    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        Wallet createdWallet = walletService.createWallet(wallet);
        return new ResponseEntity<>(createdWallet, HttpStatus.CREATED);
    }

    // Update an existing wallet
    @PutMapping("/{id}")
    public ResponseEntity<Wallet> updateWallet(@PathVariable String id, @RequestBody Wallet updatedWallet) {
        try {
            Wallet wallet = walletService.updateWallet(id, updatedWallet);
            return new ResponseEntity<>(wallet, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a wallet by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable String id) {
        try {
            walletService.deleteWallet(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add balance to a wallet
    @PostMapping("/user/{userId}/add-balance")
    public ResponseEntity<Wallet> addBalance(@PathVariable String userId, @RequestParam double amount) {
        try {
            Wallet wallet = walletService.addBalance(userId, amount);
            return new ResponseEntity<>(wallet, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Deduct balance from a wallet
    @PostMapping("/user/{userId}/deduct-balance")
    public ResponseEntity<Wallet> deductBalance(@PathVariable String userId, @RequestParam double amount) {
        try {
            Wallet wallet = walletService.deductBalance(userId, amount);
            return new ResponseEntity<>(wallet, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
