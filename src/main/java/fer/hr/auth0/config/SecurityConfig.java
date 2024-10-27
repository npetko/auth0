package fer.hr.auth0.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LogoutHandler logoutHandler;

    public SecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        return http.authorizeHttpRequests(auth -> {
            try {
                auth.requestMatchers("/api/tickets/generate").hasAuthority("SCOPE_write:generate");
                auth.requestMatchers("/api/tickets/ticket/{ticketId}").authenticated()
                        .and().oauth2Login()
                        .and().logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .addLogoutHandler(logoutHandler);
                auth.requestMatchers("/static/css/styles.css").permitAll()
                        .requestMatchers("/**").permitAll();
                auth.anyRequest().permitAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        })
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt())
                .build();
    }

}
