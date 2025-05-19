package rmit.edu.vn.hcmc_metro.userauth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import rmit.edu.vn.hcmc_metro.jwt.JwtUtil;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;


    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    public List<UserModel> createUser(List<UserModel> user) {
        return userRepository.saveAll(user);
    }

    // First-time user creation
    public List<UserModel> createUserFirst(List<UserModel> users) {
        List<UserModel> savedUsers = new ArrayList<>();
        for (UserModel user : users) {
            // Check if a user with the same email already exists
            UserModel existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null) {
                // Update the existing user's details
                existingUser.setPassword(user.getPassword());
                existingUser.setRole(user.getRole());
                existingUser.setEnabled(user.isEnabled());
                savedUsers.add(userRepository.save(existingUser)); // Save the updated user
            } else {
                // Add a new user if no matching email exists
                savedUsers.add(userRepository.save(user));
            }
        }
        return savedUsers; // Return the list of saved users
    }

    // Find user by email
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email); // Call the repository method
    }

    // Get all users
    public List<UserModel> getAllUsers() {
        return userRepository.findAll(); // Fetch all users from the repository
    }

    // Create JWT token with userId
    public String createAuthToken(UserDetails user, boolean isValidCredential, String userId) {
        if (isValidCredential) {
            return jwtUtil.generateToken(user, userId); // Pass userId to generateToken
        } else {
            return "N/A";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email);

        if (user == null) {
            return new User(UserAuthConfig.USER_AUTH_INVALID_PLACEHOLDER,
                    "",
                    new ArrayList<GrantedAuthority>());
        } else {
            // Build Spring Security User object
            return User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole()) // Assuming roles are stored as a single string
                    .disabled(!user.isEnabled())
                    .build();
        }
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
