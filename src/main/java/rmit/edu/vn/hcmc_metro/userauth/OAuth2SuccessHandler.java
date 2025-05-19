package rmit.edu.vn.hcmc_metro.userauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import rmit.edu.vn.hcmc_metro.jwt.JwtUtil;
import rmit.edu.vn.hcmc_metro.userauth.UserModel;
import rmit.edu.vn.hcmc_metro.userauth.UserRepository;

import java.io.IOException;


@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // collects information from google
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");

        // Tạo user mới nếu chưa tồn tại
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            user = new UserModel(email, null, "PASSENGER", true);
            userRepository.save(user);
        }

        // generate token and redirect to frontend
        String token = jwtUtil.generateTokenGoogle(email);
        response.sendRedirect("http://localhost:3001/oauth2-success.html?token=" + token);
    }
}