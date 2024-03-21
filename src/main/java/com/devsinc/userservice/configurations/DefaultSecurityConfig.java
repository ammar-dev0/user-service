package com.devsinc.userservice.configurations;

import com.devsinc.userservice.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class DefaultSecurityConfig {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserService userService;

    public DefaultSecurityConfig(BCryptPasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    /**
     * Configures the security filter chain.
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers("/role/**").authenticated()
                        .requestMatchers("/user/register")
                        .permitAll()
                        .requestMatchers("/sign_in/**")
                        .permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .anyRequest().authenticated()
                        ).formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                        .httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Creates a DaoAuthenticationProvider bean.
     * @return The configured DaoAuthenticationProvider.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

    /**
     * Creates an AuthenticationManager bean.
     * @param authenticationConfiguration The AuthenticationConfiguration object.
     * @return The configured AuthenticationManager.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
