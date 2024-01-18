package be.tftic.spring.demo.bll.impl;

import be.tftic.spring.demo.bll.AuthService;
import be.tftic.spring.demo.bll.exceptions.EntityAlreadyExistsException;
import be.tftic.spring.demo.dal.UserRepository;
import be.tftic.spring.demo.domain.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder encoder,
            AuthenticationManager authManager
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authManager = authManager;
    }

    @Override
    public User login(String username, String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
        auth = authManager.authenticate(auth);
        return userRepository.findByUsername(auth.getName())
                .orElseThrow();
    }

    @Override
    public User register(User user) {
        if( userRepository.existsByUsername(user.getUsername()) )
            throw new EntityAlreadyExistsException( "username", user.getUsername() );

        user.setPassword( encoder.encode(user.getPassword()) );
        return userRepository.save( user );
    }
}
