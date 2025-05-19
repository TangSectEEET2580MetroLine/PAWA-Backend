package rmit.edu.vn.hcmc_metro.userauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rmit.edu.vn.hcmc_metro.security_config.HttpOnlyCookieConfig;

@RestController
@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@RequestBody UserModel user) {


        UserModel userWithHashedPassword = new UserModel(
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getRole(),
                true
        );

        UserModel newUser = userService.createUser(userWithHashedPassword);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }



    @PostMapping("/login")
    public ResponseEntity<DtoAuthResponse> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody DtoLogin loginDto
    ) {

        String username = loginDto.getEmail();
        String password = loginDto.getPassword();

        try {
            UsernamePasswordAuthenticationToken credentialToken
                    = new UsernamePasswordAuthenticationToken(
                    username,
                    password
            );

            Authentication token = authenticationManager.authenticate(credentialToken);

            System.out.println("User Authenticated: " + token.isAuthenticated());

            if (token.isAuthenticated()) {
                // Retrieve the UserModel from the database using the email (username)
                UserModel userModel = userService.findByEmail(username);

                // Generate the JWT with the userId included
                String userAuthJwtToken = userService.createAuthToken(
                        (UserDetails) token.getPrincipal(),
                        token.isAuthenticated(),
                        userModel.getId() // Pass the userId from the UserModel
                );

                DtoAuthResponse responseDto = new DtoAuthResponse(userAuthJwtToken);

                Cookie cookie = HttpOnlyCookieConfig.createCookie(
                        UserAuthConfig.USER_AUTH_COOKIE_NAME,
                        userAuthJwtToken
                );

                response.addCookie(cookie);

                System.out.println("User Auth Successful " + userAuthJwtToken);

                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            System.err.println("Error when logging in: " + e.getMessage());

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
