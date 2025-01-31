package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Customer;
import com.dawidg90.simple_bank.model.LoginRequestDTO;
import com.dawidg90.simple_bank.model.LoginResponseDTO;
import com.dawidg90.simple_bank.repository.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dawidg90.simple_bank.constants.ApplicationConstants.JWT_HEADER;
import static com.dawidg90.simple_bank.constants.ApplicationConstants.JWT_SECRET_DEFAULT;
import static com.dawidg90.simple_bank.constants.ApplicationConstants.JWT_SECRET_KEY;

@Profile("auth-and-resource-server")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment environment;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            String hashedPassword = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashedPassword);
            customer.setCreateDate(new Date(System.currentTimeMillis()));
            Customer savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Given user details were successfully registered.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed.");
            }
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occurred: %s".formatted(exception.getMessage()));
        }
    }

    @RequestMapping("/user")
    public Customer getUSerDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequest) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if (null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            if (null != environment) {
                String secret = environment.getProperty(JWT_SECRET_KEY, JWT_SECRET_DEFAULT);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder()
                        .issuer("SimpleBank")
                        .subject("JWT Token")
                        .claim("username", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(",")))
                        .issuedAt(java.util.Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                        .expiration(java.util.Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.UTC)))
                        .signWith(secretKey)
                        .compact();
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header(JWT_HEADER, jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
    }
}
