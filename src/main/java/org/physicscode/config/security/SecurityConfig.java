package org.physicscode.config.security;

import org.physicscode.config.security.filter.JWTAuthenticationWebFilter;
import org.physicscode.domain.repository.EboostAuthenticationUserRepository;
import org.physicscode.utils.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

/**
 * This Configuration class should contain all the security constraints for the app
 * The external lib constructed filters should be added here also
 */

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JWTAuthenticationWebFilter jwtAuthenticationWebFilter;

    public SecurityConfig (SecurityUtils securityUtils,
                           EboostAuthenticationUserRepository eboostAuthenticationUserRepository) {
        this.jwtAuthenticationWebFilter = new JWTAuthenticationWebFilter(securityUtils, eboostAuthenticationUserRepository);
    }
    
    @Order(1)
    @Bean
    public JWTAuthenticationWebFilter securityFilter(SecurityUtils securityUtils,
                                      EboostAuthenticationUserRepository eboostAuthenticationUserRepository) {

        return new JWTAuthenticationWebFilter(securityUtils, eboostAuthenticationUserRepository);
    }


    @Order(2)
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http.securityMatcher(
                pathMatchers("/profile", "/profile/**", "/membership", "/membership/**")).authorizeExchange()

                .pathMatchers("/swagger-ui.html").permitAll()

                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .pathMatchers("/profile/customer").hasRole("CUSTOMER")
                .pathMatchers("/profile/freelancer", "/profile/freelancer/**").hasRole("FREELANCER")
                .pathMatchers("/membership/freelancer").hasRole("FREELANCER")
                .and()
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.FIRST)
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .logout().disable().build();
    }

    @Bean
    public CorsConfigurationSource corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
