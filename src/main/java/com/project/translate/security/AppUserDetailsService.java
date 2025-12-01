package com.project.translate.security;

import com.project.translate.model.AppUser;
import com.project.translate.repository.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser user = userRepo.findAppUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: "+ email));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_"+ user.getRole().name())
                .build();
    }

}
