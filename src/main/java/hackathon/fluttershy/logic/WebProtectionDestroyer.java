package hackathon.fluttershy.logic;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebProtectionDestroyer implements WebMvcConfigurer {
     //
     //
     // Пришлось уничтожить все намеки на безопасность, т.к. времени осталось <3 часов, а у нас ни базюка не подключена, ни сайт
     // Ужас вообще.
     //
     //

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }
}
