package rmit.edu.vn.hcmc_metro.wallet.STRIPE;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    private final String successUrl = "http://localhost:3000/payment/success";
    private final String cancelUrl = "http://localhost:3000/payment/cancel";


    // Explicitly define Stripe API key directly in service
    @Value("${stripe.secret-key}")
    private String APIkey;
    @PostConstruct
    public void init() {
        Stripe.apiKey = APIkey;
    }

    /**
     * Create a Stripe Checkout Session for the given amount (in cents).
     * Returns the session object, from which you can extract session.getId()
     * or session.getUrl() to redirect your client.
     */
    public Session createCheckoutSession(long amount) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("vnd")
                                                .setUnitAmount(amount)   // amount in the smallest currency unit
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("PAWA e-Wallet Top-Up")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        return Session.create(params);
    }
}
