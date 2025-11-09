package hackathon.fluttershy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RoomReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomReservationSystemApplication.class, args);
        System.out.println("Running");
    }
}