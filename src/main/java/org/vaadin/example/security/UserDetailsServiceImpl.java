package org.vaadin.example.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.vaadin.example.repositories.UserRepository;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //injektoi käyttäjätiedot kannasta
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //lataa käyttäjän tiedot käyttäjätunnuksen perusteella
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.vaadin.example.entities.User appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Käyttäjää ei löytynyt: " + username));

        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                        .collect(Collectors.toSet())
        );
    }
}