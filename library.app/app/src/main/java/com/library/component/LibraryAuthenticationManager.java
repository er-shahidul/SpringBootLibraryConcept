package com.library.component;


import java.util.List;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author Shahidul Hasan
 * @developer Shahidul Hasan
 * class LibraryAuthenticationManager
 * Component
 */
@Component
@AllArgsConstructor
public class LibraryAuthenticationManager implements ReactiveAuthenticationManager {

    private LibraryJWTUtil jwtUtil;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtil.getUsernameFromToken(authToken);
        return Mono.just(jwtUtil.validateToken(authToken))
            .filter(valid -> valid)
            .switchIfEmpty(Mono.empty())
            .map(valid -> {
                Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
                List<String> rolesMap = claims.get("role", List.class);
                String privilege = claims.get("privilege", String.class);
                UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
                result.setDetails(privilege);
                return result;
            });
    }
}
