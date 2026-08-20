package com.mehmetsolak.mini_chat_app.auth.config;

import com.mehmetsolak.mini_chat_app.auth.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .cors(Customizer.withDefaults());

        http.authorizeHttpRequests(requests -> {
                    requests.requestMatchers(
                            "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll();
                    requests.requestMatchers(
                            "/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/auth/logout", "/api/v1/auth/csrf").permitAll();
                    requests.requestMatchers("/api/v1/public/**").permitAll();
                    requests.requestMatchers("/ws/**").fullyAuthenticated();
                    requests.requestMatchers("/api/**").fullyAuthenticated();
                    requests.anyRequest().denyAll();
                }
        );

        http.securityContext(context -> context.securityContextRepository(securityContextRepository()));

        http.sessionManagement(session -> {
            session.maximumSessions(1).maxSessionsPreventsLogin(true);
            session.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession);
            session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        });

        http.logout(logout -> {
            logout.logoutUrl("/api/v1/auth/logout");
            logout.logoutSuccessHandler(((request, response, authentication) -> {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }));
            logout.addLogoutHandler(
                    new HeaderWriterLogoutHandler(
                            new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES)
                    )
            );
            logout.deleteCookies("JSESSIONID");
        });

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityContextHolderStrategy securityContextHolderStrategy() {
        return SecurityContextHolder.getContextHolderStrategy();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
