package ca.ucalgary.ensf480.flightapp.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

				// Realistically, csrf and cors would not be disabled like this.
				http
				.csrf((csrf) -> csrf.disable())
				.cors((cors) -> cors.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/public/**").permitAll()
						.requestMatchers("/api/admin/**").hasRole("ADMIN")
						.requestMatchers("/api/agent/**").hasRole("AGENT")
            .requestMatchers("/api/**").hasRole("USER")
						
						.anyRequest().authenticated()
				)
				.formLogin(Customizer.withDefaults())
				.rememberMe(Customizer.withDefaults());

        return http.build();
    }

	
}