package hackathon.fluttershy;

import hackathon.fluttershy.logic.DataAccess;
import hackathon.fluttershy.data.TestingDataAccess;
import hackathon.fluttershy.front.TelegramBot;
import hackathon.fluttershy.logic.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Config {
    private Server server;

    @Bean
    public Server server(DataAccess dataAccess) {
        server = new Server(dataAccess);
        return server;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateServer() {
        server.clearOldBookings();
    }

    @Bean
    public TestingDataAccess testingDataAccess() {
        return new TestingDataAccess();
    }

    @Bean
    public TelegramBot telegramBot(Server server) {
        return new TelegramBot(server);
    }
}
