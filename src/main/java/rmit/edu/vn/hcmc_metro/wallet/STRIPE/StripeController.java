package rmit.edu.vn.hcmc_metro.wallet.STRIPE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody Map<String, Long> body) {
        long amount = body.getOrDefault("amount", 0L);
        try {
            Session session = stripeService.createCheckoutSession(amount);
            return ResponseEntity.ok(Map.of(
                    "sessionId", session.getId(),
                    "checkoutUrl", session.getUrl()
            ));
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
