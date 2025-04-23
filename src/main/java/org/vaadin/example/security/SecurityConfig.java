package org.vaadin.example.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.vaadin.example.views.LoginView;

//Spring security konfiguraatiot
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private final UserDetailsServiceImpl userDetailsService;

    //salasanojen salaus
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //injektoi käyttäjätiedot
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    //konfiguraatiot
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/api/**").permitAll()
            .requestMatchers("/measurements/**").hasRole("ADMIN") // Vain adminille pääsy mittauksiin
            .requestMatchers(HttpMethod.POST, "/api/measurements").permitAll() 
        ).logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .permitAll()
    ).httpBasic();

        super.configure(http);

        setLoginView(http, LoginView.class);
    }
}