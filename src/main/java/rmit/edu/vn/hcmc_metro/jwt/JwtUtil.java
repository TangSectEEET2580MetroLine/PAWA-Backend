package rmit.edu.vn.hcmc_metro.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

	@Autowired
	private KeyStoreManager keyStoreManager;

	// private final String secretSymmetricKey = "thisIsMyWonderfulTopSecretKeyEver";
	//	private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String generateToken(UserDetails userDetails, String userId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", userDetails.getAuthorities());
		claims.put("userId", userId); // Add userId as a claim
	
		return createToken(claims, userDetails.getUsername());
	}
	
	private String createToken(Map<String, Object> claims, String subject) {
		String jwt = Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuer("SpringAuthorizationDemo")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				//set time(change if needed)
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
				// .signWith(SignatureAlgorithm.HS256, secretSymmetricKey.getBytes())
				.signWith(keyStoreManager.getPrivateKey(), SignatureAlgorithm.RS256)
				.compact();
		return jwt;
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token)  {
		return loadJwtParser()
				.parseClaimsJws(token)
				.getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private JwtParser loadJwtParser() {
		return Jwts.parserBuilder()
				.setSigningKey(keyStoreManager.getPublicKey())
				.build();
	}

	public boolean verifyJwt(String token) {
		JwtParser jwtParser = loadJwtParser();
		try {
			jwtParser.isSigned(token);
			isTokenExpired(token);
			extractUsername(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
