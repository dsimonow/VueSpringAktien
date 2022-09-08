package se.adesso.aktienverwaltung.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import se.adesso.aktienverwaltung.model.User;
import se.adesso.aktienverwaltung.persistence.repository.UserRepository;

import java.util.Collections;
import java.util.Objects;

public class AVAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) authentication;
        final String name = (String) authentication.getPrincipal();

        final String password = (String) upAuth.getCredentials();

        final String storedPassword = userRepository.findByUserName(name).map(User::getPassword)
                .orElseThrow(() -> new BadCredentialsException("illegal id or passowrd"));

        if (Objects.equals(password, "") || !Objects.equals(password, storedPassword)) {
            throw new BadCredentialsException("illegal id or passowrd");
        }

        final Object principal = authentication.getPrincipal();
        final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                principal, authentication.getCredentials(),
                Collections.emptyList());
        result.setDetails(authentication.getDetails());

        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


}
