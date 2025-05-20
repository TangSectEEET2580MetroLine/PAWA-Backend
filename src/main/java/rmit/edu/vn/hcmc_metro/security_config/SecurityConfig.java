package rmit.edu.vn.hcmc_metro.security_config;

/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import rmit.edu.vn.hcmc_metro.filter.AuthRequestFilter;
import rmit.edu.vn.hcmc_metro.security_config.RoleConfig;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import rmit.edu.vn.hcmc_metro.filter.AuthRequestFilter;
import rmit.edu.vn.hcmc_metro.security_config.RoleConfig;
import rmit.edu.vn.hcmc_metro.userauth.OAuth2SuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AuthRequestFilter authRequestFilter;

	@Autowired
	private OAuth2SuccessHandler oAuth2SuccessHandler;

	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// 1) Enable CORS (will use your WebMvcConfigurer CorsConfiguration)
				.cors(Customizer.withDefaults())

				// 2) Disable CSRF (we’re a stateless JSON API)
				.csrf(AbstractHttpConfigurer::disable)

				// 3) Turn off Spring Security’s default form-login
				.formLogin(AbstractHttpConfigurer::disable)

				// 4) Define public vs. protected endpoints
				.authorizeHttpRequests(auth -> auth
						// JWT login & register
						.requestMatchers("/api/auth/login").permitAll()
						.requestMatchers("/api/auth/register").permitAll()
						// **OAuth2 endpoints must be public** so the redirect flow works
						.requestMatchers("/oauth2/**").permitAll()

						// Other public APIs
						.requestMatchers("/api/tickets/**").permitAll()
						.requestMatchers("/metro-line/**").permitAll()
						.requestMatchers("/api/wallets/**").permitAll()
						.requestMatchers("/api/ticket-carts/**").permitAll()
						.requestMatchers("/api/ticket-types/**").permitAll()
						.requestMatchers("/api/payments/**").permitAll()
						.requestMatchers("/passenger/*/upload-id").permitAll()
						.requestMatchers("/passenger/*/update-id").permitAll()
						.requestMatchers("/passenger/profile/{userId})").permitAll()

						// Passenger-only endpoints
						.requestMatchers("/passenger/**")
						.hasAnyRole(RoleConfig.ADMIN.name(), RoleConfig.PASSENGER.name())

						// Everything else requires authentication
						.anyRequest().authenticated()
				)


				// 5) Plug in your JWT filter
				.addFilterBefore(authRequestFilter, UsernamePasswordAuthenticationFilter.class)

				// 6) Hook up Spring Security’s OAuth2 login
				.oauth2Login(oauth2 -> oauth2
						/*// this matches GET /oauth2/authorization/{registrationId}
						.authorizationEndpoint(endpoint -> endpoint
								.baseUri("/oauth2/authorization")
						)
						// this matches your redirect URI (e.g. /oauth2/callback/{registrationId})
						.redirectionEndpoint(endpoint -> endpoint
								.baseUri("/oauth2/callback/*")
						)*/
						// on success, hand off to your custom success handler
						.successHandler(oAuth2SuccessHandler)
				);


		return http.build();
	}
}
