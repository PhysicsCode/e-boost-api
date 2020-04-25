package org.physicscode.config.security.filter;

import com.auth0.jwt.JWT;
import lombok.RequiredArgsConstructor;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.physicscode.domain.repository.EboostAuthenticationUserRepository;
import org.physicscode.utils.SecurityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Component
public class JWTAuthenticationWebFilter implements WebFilter {

    private static final EBoostAuthenticationUser ANONYMOUS_AUTHENTICATION_TOKEN =  EBoostAuthenticationUser.buildAnonymous();

    private final SecurityUtils securityUtils;
    private final EboostAuthenticationUserRepository eboostAuthenticationUserRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        AtomicReference<String> userToken = new AtomicReference<>("");

        Optional.ofNullable(exchange.getRequest().getHeaders().get("Authorization"))
                .map(headerList -> headerList.get(0))
                .ifPresent(token -> userToken.set(token.replace("Bearer: ", "")));

        try {
            String userId = JWT.require(securityUtils.obtainAlgorithm())
                    .build()
                    .verify(userToken.get())
                    .getSubject();

            if (userId == null) {
                return chain.filter(exchange);
            }

            return eboostAuthenticationUserRepository.findByUserId(userId)
                    .defaultIfEmpty(ANONYMOUS_AUTHENTICATION_TOKEN)
                    .flatMap(user -> chain.filter(exchange).subscriberContext(ReactiveSecurityContextHolder.withAuthentication(user))
                    );

        } catch (Exception e) {
            return chain.filter(exchange);
        }
    }
}
