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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AuthRequestFilter authRequestFilter;


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder defaultEncoder() {
			return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/login", "/register").permitAll()
				.requestMatchers("/passenger/**").hasAnyRole(
					RoleConfig.ADMIN.name(), 
					RoleConfig.PASSENGER.name()
				)
				.anyRequest().authenticated()
			);

		http.addFilterBefore(authRequestFilter, 
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
    }  
}