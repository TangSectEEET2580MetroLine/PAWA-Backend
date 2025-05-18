package rmit.edu.vn.hcmc_metro.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		//  Completely skip JWT authentication for the purchase endpoint
		if ("/tickets/purchase".equals(path)) {
			System.out.println(" Skipping AuthRequestFilter for: " + path);
			filterChain.doFilter(request, response);
			return;
		}

		// --- JWT extraction & validation ---
		String token = null;
		boolean isValidToken = false;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie ck : cookies) {
				if (UserAuthConfig.USER_AUTH_COOKIE_NAME.equals(ck.getName())) {
					token = ck.getValue();
					isValidToken = jwtUtil.verifyJwt(token);
					break;
				}
			}
		}

		if (token != null && isValidToken
				&& SecurityContextHolder.getContext().getAuthentication() == null) {

			String username = jwtUtil.extractUsername(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (userDetails != null) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(
								userDetails,
								null,
								userDetails.getAuthorities()
						);
				authToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);
				SecurityContextHolder.getContext().setAuthentication(authToken);
				System.out.println("✅ Authenticated user: " + username);
			}
		}

		filterChain.doFilter(request, response);
	}
}
