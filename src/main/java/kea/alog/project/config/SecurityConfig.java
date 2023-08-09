package kea.alog.project.config;

import java.util.Arrays;
import kea.alog.project.common.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    private static final String[] AUTH_WHITELIST = {
        // -- Swagger UI v3
        "/v3/api-docs/**",
        "v3/api-docs/**",
        "/swagger-ui/**",
        "swagger-ui/**",
        "/api/projects/**"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf().disable()   // token 사용 방식이라 csrf disable
            .exceptionHandling()
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()  // session 사용하지 않기에 STATELESS
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
            Arrays.asList("http://localhost:3000", "http://127.0.0.1:3000",
                "https://alog.acceler.kr"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
