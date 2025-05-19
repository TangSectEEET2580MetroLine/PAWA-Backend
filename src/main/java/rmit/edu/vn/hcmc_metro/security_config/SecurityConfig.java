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
import rmit.edu.vn.hcmc_metro.userauth.OAuth2SuccessHandler;

import rmit.edu.vn.hcmc_metro.filter.AuthRequestFilter;
import rmit.edu.vn.hcmc_metro.security_config.RoleConfig;

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
	public BCryptPasswordEncoder defaultEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(requests -> requests
						.requestMatchers(
								"/api/auth/login",
								"/api/auth/register",
								"/api/tickets/**",
								"/metro-line/**",
								"/api/wallets/**",
								"/api/ticket-carts/**",
								"/api/ticket-types/**",
								"/passenger/*/upload-id",
								"/oauth2/**",
								"/login/oauth2/**"
						).permitAll()

						.requestMatchers("/passenger/**").hasAnyRole(
								RoleConfig.ADMIN.name(),
								RoleConfig.PASSENGER.name()
						)

						.anyRequest().authenticated()
				)

				.oauth2Login(oauth -> oauth.successHandler(oAuth2SuccessHandler));

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
