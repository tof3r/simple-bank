package com.dawidg90.simple_bank.config;

import com.dawidg90.simple_bank.exceptionhandling.MyAccessDeniedHandler;
import com.dawidg90.simple_bank.exceptionhandling.MyBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(smc -> smc.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::changeSessionId)
                .invalidSessionUrl("/invalidSession")
                .maximumSessions(5)
                .maxSessionsPreventsLogin(true));
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/account", "/balance", "/cards", "/loans").authenticated()
                .requestMatchers("/contact", "/notices", "/error", "/register", "/invalidSession").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new MyBasicAuthenticationEntryPoint()));
        http.csrf(csrfCfg -> csrfCfg.disable());
        http.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure());
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new MyAccessDeniedHandler()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
