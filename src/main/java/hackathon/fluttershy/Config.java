package hackathon.fluttershy;

import hackathon.fluttershy.data.DataAccess;
import hackathon.fluttershy.data.DataAccessImpl;
import hackathon.fluttershy.data.TestingDataAccess;
import hackathon.fluttershy.logic.Server;
import hackathon.fluttershy.logic.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class Config {

    @Bean
    public Server server(DataAccess dataAccess, Users users) {
        return new Server(dataAccess, users);
    }
    // Тут я удалил бин DataAccess тк он сам генерируется из-за аннотаций

    @Bean
    public Users users() {
        return new Users();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
