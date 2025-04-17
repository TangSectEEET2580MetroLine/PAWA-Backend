package rmit.edu.vn.hcmc_metro.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rmit.edu.vn.hcmc_metro.jwt.JwtUtil;
import rmit.edu.vn.hcmc_metro.userauth.UserAuthConfig;

@Component
public class AuthRequestFilter extends OncePerRequestFilter { 
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = null;

		boolean isValidToken = false;

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			
			for (Cookie ck : cookies) {
				if (UserAuthConfig.USER_AUTH_COOKIE_NAME.equals(ck.getName())) {
					token = ck.getValue();
					isValidToken = jwtUtil.verifyJwt(token);
				}
			}
			
			if (
				token != null && isValidToken
				&& SecurityContextHolder.getContext().getAuthentication() == null
			) {

				String username = jwtUtil.extractUsername(token);

				System.out.println("Username in Filter " + username);
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (userDetails == null) {
					System.out.println("UserDetails is null, invalid token");
					return;
				}
						
				UsernamePasswordAuthenticationToken emailAuthToken =
						new UsernamePasswordAuthenticationToken(
								userDetails,
								null,
								userDetails.getAuthorities()
						);
	
				// Add extra details into the token, in this case, we add the request information
				emailAuthToken.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(emailAuthToken);
				
				System.out.println(String.format("Authenticate Token Set:\n"
								+ "Email: %s\n"
								+ "Authority: %s\n",
						userDetails.getUsername(),
						userDetails.getAuthorities().toString()));
			}
		}
		filterChain.doFilter(request, response);
	}
}
