package pe.edu.upc.goals_service.shared.infrastructure.authorization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.function.Supplier;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Bean
    public InternalServiceAuthenticationFilter internalServiceAuthenticationFilter(
            @Value("${authorization.internal-service.secret}") String internalSecret) {
        return new InternalServiceAuthenticationFilter(internalSecret);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            InternalServiceAuthenticationFilter internalServiceAuthenticationFilter,
            @Value("${legacy.jwt.issuer:iam-service}") String legacyJwtIssuer
    ) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/actuator/**"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/goals")
                .access((authentication, context) -> hasReadGoalsOrLegacyJwt((Supplier<Authentication>) authentication, context, legacyJwtIssuer))
                .anyRequest().authenticated());

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
                jwt.jwtAuthenticationConverter(GoalsJwtAuthenticationConverter.jwtAuthenticationConverter())));
        http.addFilterBefore(internalServiceAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private AuthorizationDecision hasReadGoalsOrLegacyJwt(
            Supplier<Authentication> authenticationSupplier,
            RequestAuthorizationContext context,
            String legacyJwtIssuer) {
        var authentication = authenticationSupplier.get();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        if (hasAuthority(authentication, "read:goals")
                || hasAuthority(authentication, "ROLE_SERVICE")
                || isLegacyJwt(authentication, legacyJwtIssuer)) {
            return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(false);
    }

    private boolean hasAuthority(Authentication authentication, String expectedAuthority) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> expectedAuthority.equals(authority.getAuthority()));
    }

    private boolean isLegacyJwt(Authentication authentication, String legacyJwtIssuer) {
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthentication)) {
            return false;
        }
        var issuer = jwtAuthentication.getToken().getClaimAsString("iss");
        return legacyJwtIssuer.equals(issuer);
    }
}
