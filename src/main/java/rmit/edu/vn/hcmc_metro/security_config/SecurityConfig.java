package rmit.edu.vn.hcmc_metro.security_config;

import org.springframework.beans.factory.annotation.Autowired;
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
import rmit.edu.vn.hcmc_metro.security_config.RoleConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AuthRequestFilter authRequestFilter;

	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder defaultEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// Disable CSRF for our REST API
				.csrf(csrf -> csrf.disable())

				// 1) Public endpoints: login, register, tickets/**
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
						.requestMatchers("/api/tickets/**").permitAll()
						.requestMatchers("/metro-line/**").permitAll()
						.requestMatchers("/api/wallets/**").permitAll()
						.requestMatchers("/api/ticket-carts/**").permitAll()
						.requestMatchers("/api/ticket-types/**").permitAll()
						.requestMatchers("/api/payments/**").permitAll()
						.requestMatchers("/passenger/*/upload-id").permitAll()
						.requestMatchers("/passenger/*/update-id").permitAll()
						// Passenger-only endpoints
						.requestMatchers("/passenger/**").hasAnyRole(
								RoleConfig.ADMIN.name(),
								RoleConfig.PASSENGER.name()
						)
						// All other endpoints require authentication
						.anyRequest().authenticated()
				);

		// 2) Insert our JWT filter before the UsernamePasswordAuthenticationFilter
		http.addFilterBefore(authRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/*@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				// **ALLOW EVERY REQUEST** (no security at all)
				.authorizeHttpRequests(auth -> auth
						.anyRequest().permitAll()
				);
		return http.build();
	}*/
}
