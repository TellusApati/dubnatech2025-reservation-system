package hackathon.fluttershy.front;

import org.apache.http.client.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.telegram.abilitybots.api.bot.AbilityBot;

public class TelegramBot extends AbilityBot {

    //private final ResponseHandler responseHandler;

    @Autowired
    protected TelegramBot(Environment env) {
        super(env.getProperty("telegramBotToken"), "ReservationBot");
        //responseHandler = new ResponseHandler(silent);
    }

    @Override
    public long creatorId() {
        return 1L;
    }
}
