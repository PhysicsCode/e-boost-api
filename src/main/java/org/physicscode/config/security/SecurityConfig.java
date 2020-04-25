package org.physicscode.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

/**
 * This Configuration class should contain all the security constraints for the app
 * The external lib constructed filters should be added here also
 */

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http = http.securityMatcher(
                pathMatchers("/profile/**")).authorizeExchange()

                //.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //.pathMatchers(HttpMethod.POST,"/auth/login").permitAll()
                //.pathMatchers(HttpMethod.POST,"/auth/register/**").permitAll()

                .pathMatchers(HttpMethod.GET, "/profile/customer").hasRole("CUSTOMER")
                .pathMatchers(HttpMethod.GET, "/profile/freelancer").hasRole("FREELANCER")
                .and();

        return http.csrf().disable()
                .headers().frameOptions().disable()
                .and().build();
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
