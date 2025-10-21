package com.cafeteriatracker.config;

import com.cafeteriatracker.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Explicitly defines the authentication provider using your service and encoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider authenticationProvider) throws Exception {
        http
                // Inject the custom authentication provider
                .authenticationProvider(authenticationProvider)

                .authorizeHttpRequests((requests) -> requests
                        // Public paths that do not require authentication
                        .requestMatchers(
                                "/", "/welcome", "/login", "/signup", "/css/**", "/js/**"
                        ).permitAll()
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )

                // Re-enable Form-based Login configuration
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page URL
                        .loginProcessingUrl("/login") // URL the login form POSTs to
                        .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
                        .permitAll()
                )

                // Re-enable Logout configuration
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout") // Redirect after successful logout
                        .permitAll()
                )

                // Required for running inside an iframe (like the Canvas)
                .csrf(csrf -> csrf.disable());

        http.headers(headers -> headers.frameOptions().disable());

        return http.build();
    }
}
