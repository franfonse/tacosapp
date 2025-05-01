package com.franfonse.tacosapp.config;

import com.franfonse.tacosapp.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService uds,
            PasswordEncoder encoder) {
        DaoAuthenticationProvider ap = new DaoAuthenticationProvider();
        ap.setUserDetailsService(uds);
        ap.setPasswordEncoder(encoder);
        return ap;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception {
        http
                .authenticationProvider(authProvider)

                // public URLs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",                     // your index
                                "/user/login",           // GET → login.html
                                "/user/login?error",     // login failures
                                "/user/signup",          // GET → signup.html & POST handler
                                "/user/signup?success",  // optional post-signup redirect
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // custom login form configuration
                .formLogin(form -> form
                        .loginPage("/user/login")          // serve your login.html
                        .loginProcessingUrl("/user/login") // POST target of your form
                        .usernameParameter("username")     // name of the user field
                        .passwordParameter("password")     // name of the pass field
                        .defaultSuccessUrl("/", true)      // where to go on successful login
                        .failureUrl("/user/login?error")   // where to go on auth failure
                        .permitAll()
                )

                // logout settings
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
