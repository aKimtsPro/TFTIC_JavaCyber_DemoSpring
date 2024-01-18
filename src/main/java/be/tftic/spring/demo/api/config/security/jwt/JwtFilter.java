package be.tftic.spring.demo.api.config.security.jwt;

import be.tftic.spring.demo.bll.UserService;
import be.tftic.spring.demo.domain.entity.User;
import be.tftic.spring.demo.utils.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    public JwtFilter(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse resp,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = jwtProvider.extractToken(req);

        if( token != null && jwtProvider.validateToken(token) ){
            String username = jwtProvider.getUsername(token);
            User user = userService.getByUsername(username);
            Authentication auth = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(req,resp);
    }
}
