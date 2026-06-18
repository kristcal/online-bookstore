package njt.mavenproject2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Konfiguracija bezbednosti aplikacije.
 *
 * Definiše pravila pristupa REST API rutama, podešava CORS
 * i onemogućava CSRF zaštitu za potrebe REST komunikacije.
 *
 * Trenutno su sve rute dostupne bez autentifikacije,
 * dok su login i pregled knjiga eksplicitno dozvoljeni.
 *
 * @author Korisnik
 */
@Configuration
public class SecurityConfig {

    /**
     * Konfiguriše sigurnosni filter lanac aplikacije.
     *
     * Isključuje CSRF zaštitu, omogućava CORS podršku
     * i definiše pravila pristupa API rutama.
     *
     * @param http objekat za konfiguraciju HTTP bezbednosti
     * @return konfigurisan sigurnosni filter lanac
     * @throws Exception ukoliko dođe do greške prilikom konfiguracije
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // isključi CSRF za REST API
            .csrf(csrf -> csrf.disable())

            // omogući CORS
            .cors(Customizer.withDefaults())

            // pravila pristupa rutama
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/knjige/**").permitAll()
                .anyRequest().permitAll()
            );

        return http.build();
    }

    /**
     * Konfiguriše CORS pravila za frontend aplikaciju.
     *
     * Dozvoljava zahteve sa lokalnog React frontenda,
     * definiše podržane HTTP metode i zaglavlja.
     *
     * @return izvor CORS konfiguracije
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        cfg.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://172.20.10.3:3000"
        ));

        cfg.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));

        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/api/**", cfg);

        return source;
    }
}