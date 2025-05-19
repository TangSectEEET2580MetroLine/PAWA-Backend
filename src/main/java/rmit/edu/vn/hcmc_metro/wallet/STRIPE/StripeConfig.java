package rmit.edu.vn.hcmc_metro.wallet.STRIPE;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.stripe.Stripe;

@Configuration
public class StripeConfig {


    @Value("${stripe.secret-key}")
    private String APIkey;

    @PostConstruct
    public void init() {
        // Directly using explicit API key (hardcoded)
        Stripe.apiKey = APIkey;
    }
}
