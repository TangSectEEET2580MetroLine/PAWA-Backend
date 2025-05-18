package rmit.edu.vn.hcmc_metro.userauth;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import rmit.edu.vn.hcmc_metro.Passenger.Passenger;
import rmit.edu.vn.hcmc_metro.Passenger.PassengerService;
import rmit.edu.vn.hcmc_metro.Passenger.RegistrationRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserService           userService;
    @Autowired private PassengerService      passengerService;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    // ─── Registration ────────────────────────────────────────────────────────────
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegistrationRequest req,
            BindingResult br
    ) {
        // 1) Bean‐validation errors?
        if (br.hasErrors()) {
            List<String> errors = br.getFieldErrors().stream()
                    .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        // 2) Age check (>= 6 years)
        LocalDate sixYearsAgo = LocalDate.now().minusYears(6);
        if (req.getDateOfBirth().isAfter(sixYearsAgo)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Date of birth indicates age < 6");
        }

        // 3) Unique email & nationalId?
        if (userService.findByEmail(req.getEmail()) != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already registered");
        }
        if (passengerService.existsByNationalId(req.getNationalId())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("National ID already registered");
        }

        // 4) Create Passenger record
        Passenger passenger = new Passenger();
        String passengerId = UUID.randomUUID().toString();
        passenger.setUserId(passengerId);
        passenger.setFirstName(req.getFirstName());
        passenger.setMiddleName(req.getMiddleName());
        passenger.setLastName(req.getLastName());
        passenger.setNationalId(req.getNationalId());
        passenger.setDateOfBirth(req.getDateOfBirth());
        passenger.setResidenceAddress(req.getResidenceAddress());
        passenger.setPhoneNumber(req.getPhoneNumber());
        passenger.setStudentId(req.getStudentId());
        passenger.setDisabilityStatus(req.getDisabilityStatus());
        passenger.setRevolutionaryContributionStatus(
                req.getRevolutionaryContributionStatus()
        );
        passengerService.addPassenger(passenger);

        // 5) Create UserModel + hash password
        UserModel user = new UserModel();
        user.setId(passengerId);
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("PASSENGER");
        user.setEnabled(true);
        userService.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Registration successful for userId: " + passengerId);
    }

    // ─── Login ───────────────────────────────────────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DtoLogin loginDto) {
        try {
            // 1) authenticate email/password
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

            // 2) successful: build JWT
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            UserModel  userModel   = userService.findByEmail(userDetails.getUsername());
            String     token       = userService.createAuthToken(
                    userDetails,
                    true,
                    userModel.getId()
            );

            // 3) return it in your DTO
            DtoAuthResponse resp = new DtoAuthResponse(token);
            return ResponseEntity.ok(resp);

        } catch (BadCredentialsException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }
}
