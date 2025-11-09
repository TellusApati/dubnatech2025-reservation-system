package hackathon.fluttershy.front;

import hackathon.fluttershy.logic.BookingException;
import hackathon.fluttershy.logic.ConnectionType;
import hackathon.fluttershy.logic.Server;
import hackathon.fluttershy.logic.datatypes.Booking;
import hackathon.fluttershy.logic.datatypes.Building;
import hackathon.fluttershy.logic.datatypes.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    enum SessionType {
        MAIN,
        BUILDING_CHOICE,
        DATE_CHOICE,
        TIME_CHOICE,
        ROOM_CHOICE,
        MY_BOOKINGS,
        CANCEL_BOOKINGS
    }

    private final HashMap<String, UserData> usersData = new HashMap<>();

    private final TelegramClient telegramClient;
    private final Server server;

    private final String BOT_TOKEN = "";

    public TelegramBot(Server server) {
        telegramClient = new OkHttpTelegramClient(getBotToken());
        this.server = server;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String username = update.getMessage().getFrom().getUserName();
            long chatId = update.getMessage().getChatId();

            if (server.hasExternalConnection(ConnectionType.TELEGRAM, username)) {
                String messageText = update.getMessage().getText().toLowerCase();
                UserData userData = usersData.get(username);

                switch (messageText) {
                    case "/back", "назад", "/start", "начать":
                        startOption(chatId, username);
                        break;
                    case "/book", "забронировать":
                        if (sessionType(username, SessionType.MAIN)) {
                            bookingBuildingOption(chatId, userData);
                            break;
                        }
                    case "/my_bookings", "мои брони":
                        if (sessionType(username, SessionType.MAIN)) {
                            myBookingsOption(chatId, username);
                            break;
                        }
                    case "/cancel", "отменить бронь":
                        if (sessionType(username, SessionType.MY_BOOKINGS)) {
                            cancelBookingOption(chatId, username);
                            break;
                        }
                    default:
                        if (userData != null && userData.getPossibleAnswers().containsKey(messageText)) {
                            switch (userData.getSessionType()) {
                                case BUILDING_CHOICE -> bookingDateOption(chatId, userData, messageText);
                                case TIME_CHOICE -> bookingRoomOption(chatId, userData, messageText);
                                case ROOM_CHOICE -> finishBooking(chatId, userData, messageText, username);
                                case CANCEL_BOOKINGS -> cancelBookingOption(chatId, username, messageText, true);
                            }
                        } else if (userData != null && sessionType(username, SessionType.DATE_CHOICE)) {
                            bookingTimeOption(chatId, userData, messageText);
                        } else {
                            unknownCommand(chatId, username);
                        }
                        break;
                }

            } else {
                sendMessage(generateMessage("Внимание, вы не указаны в системе компании. Укажите имя пользователя телеграмма в личном кабинете сайта компании: @" + update.getMessage().getFrom().getUserName(), chatId));
            }
        }
    }

    private boolean sessionType(String username, SessionType sessionType) {
        return usersData.get(username).getSessionType() == sessionType;
    }

    private void startOption(long chatId, String username) {
        SendMessage message = generateMessage("Добро пожаловать в телеграм-бот системы бронирования переговорных комнат компании FlutterShy Inc. (не настоящая компания!)", chatId);
        message.setReplyMarkup(generateKeyboardMarkup(1, new String[]{"Забронировать", "Мои брони"}));
        usersData.put(username, new UserData());
        usersData.get(username).setSessionType(SessionType.MAIN);
        sendMessage(message);
    }

    private void bookingBuildingOption(long chatId, UserData userData) {
        StringBuilder text = new StringBuilder("Выберите офис:\n");
        Building[] buildings = server.getBuildings();
        String[] buttons = new String[buildings.length+1];

        userData.getPossibleAnswers().clear();
        for (int i = 0; i < buildings.length; i ++) {
            buttons[i] = buildings[i].getName();
            userData.getPossibleAnswers().put(buildings[i].getName().toLowerCase(), buildings[i]);
            text.append("----------\n");
            text.append(buildings[i].getName()).append("\n");
            text.append(buildings[i].getDescription()).append("\n");
        }
        text.append("----------");
        buttons[buttons.length-1] = "Назад";

        userData.setSessionType(SessionType.BUILDING_CHOICE);
        SendMessage message = generateMessage(text.toString(), chatId);
        message.setReplyMarkup(generateKeyboardMarkup(1, buttons));
        sendMessage(message);
    }

    private void bookingDateOption(long chatId, UserData userData, String inputText) {
        if (inputText != null) {
            userData.setBuilding((Building) userData.getPossibleAnswers().get(inputText));
        }
        userData.setSessionType(SessionType.DATE_CHOICE);
        SendMessage message = generateMessage("Укажите дату бронирования в формате DD.MM\nDD - День\nMM - Месяц", chatId);
        message.setReplyMarkup(generateKeyboardMarkup(1, new String[] {"Назад"}));
        sendMessage(message);
    }

    private void bookingTimeOption(long chatId, UserData userData, String inputText) {
        if (inputText.length() == 5 && inputText.toCharArray()[2] == '.') {
            String[] numbers = inputText.split("\\.");
            try {
                int day = Integer.parseInt(numbers[0]);
                int month = Integer.parseInt(numbers[0]);
                userData.setDate(LocalDate.now().withDayOfMonth(day).withMonth(month));

                List<String> buttons = new ArrayList<>();

                userData.getPossibleAnswers().clear();
                for (int hour = userData.getBuilding().getOpeningHour(); hour < userData.getBuilding().getClosingHour(); hour ++) {
                    buttons.add(hour + ":00");
                    userData.getPossibleAnswers().put(hour + ":00", hour);
                }
                buttons.add("Назад");

                userData.setSessionType(SessionType.TIME_CHOICE);
                SendMessage message = generateMessage("Выберите время", chatId);
                message.setReplyMarkup(generateKeyboardMarkup(4, buttons.toArray(String[]::new)));
                sendMessage(message);

            } catch (NumberFormatException e) {
                sendMessage(generateMessage("Неверно указана дата, попробуйте ещё раз", chatId));
                bookingDateOption(chatId, userData, null);
            }
        } else {
            sendMessage(generateMessage("Неверно указана дата, попробуйте ещё раз", chatId));
            bookingDateOption(chatId, userData, null);
        }
    }

    private void bookingRoomOption(long chatId, UserData userData, String inputText) {
        userData.setStartHour((Integer) userData.getPossibleAnswers().get(inputText));

        Room[] rooms = server.getRooms(userData.getBuilding());

        String[] buttons = new String[rooms.length+1];
        StringBuilder text = new StringBuilder("Выберите комнату:\n");

        userData.getPossibleAnswers().clear();
        for (int i = 0; i < rooms.length; i ++) {
            buttons[i] = rooms[i].getName();
            userData.getPossibleAnswers().put(rooms[i].getName().toLowerCase(), rooms[i]);
            text.append("----------\n");
            text.append(rooms[i].getName()).append("\n");
            text.append(rooms[i].getDescription()).append("\n");
        }
        text.append("----------");
        buttons[buttons.length-1] = "Назад";

        userData.setSessionType(SessionType.ROOM_CHOICE);
        SendMessage message = generateMessage(text.toString(), chatId);
        message.setReplyMarkup(generateKeyboardMarkup(2, buttons));
        sendMessage(message);
    }

    private void finishBooking(long chatId, UserData userData, String inputText, String username) {
        try {
            server.bookRoom((Room) userData.getPossibleAnswers().get(inputText), userData.getDate(), userData.getStartHour(), ConnectionType.TELEGRAM, username);
            sendMessage(generateMessage("Комната успешно забронирована", chatId));
            startOption(chatId, username);
        } catch (BookingException e) {
            sendMessage(generateMessage("Не удалось забронировать комнату, возможно она уже забронирована", chatId));
        }
    }

    private void bookRoomOption(long chatId, String username) {
        StringBuilder messageText= new StringBuilder("Выберите комнату:\n");
        UserData userData = usersData.get(username);
        Room[] rooms = server.getRooms(userData.getBuilding());

        String[] buttonNames = new String[rooms.length+1];

        usersData.get(username).getPossibleAnswers().clear();
        for (int i = 0; i < rooms.length; i ++) {
            buttonNames[i] = rooms[i].getName();
            messageText.append("----------\n");
            messageText.append(rooms[i].getName()).append("\n").append(rooms[i].getDescription()).append("\n");

            usersData.get(username).getPossibleAnswers().put(rooms[i].getName().toLowerCase(), rooms[i]);
        }
        messageText.append("----------");
        buttonNames[buttonNames.length-1] = "Назад";

        SendMessage sendMessage = generateMessage(messageText.toString(), chatId);
        sendMessage.setReplyMarkup(generateKeyboardMarkup(1, buttonNames));
        userData.setSessionType(SessionType.ROOM_CHOICE);
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void myBookingsOption(long chatId, String username) {
        String messageText= "Забронированные комнаты:\n";
        UserData userData = usersData.get(username);
        Booking[] bookings = server.getBookings(username);
        if (bookings.length > 0) {
            for (int i = 0; i < bookings.length; i ++) {
                messageText += "----------\n";
                messageText += bookings[i].getRoom().getName() + "\n";
                messageText += bookings[i].getRoom().getDescription() + "\n";
                messageText += "На " + bookings[i].getDate().getDayOfMonth() + "." + bookings[i].getDate().getMonth().getValue() + "\n";
                messageText += bookings[i].getHour() + ":00-" + (bookings[i].getHour()+1) + ":00\n";
            }
            messageText += "----------";

            SendMessage message = generateMessage(messageText, chatId);
            message.setReplyMarkup(generateKeyboardMarkup(1, new String[] {"Отменить Бронь", "Назад"}));
            userData.setSessionType(SessionType.MY_BOOKINGS);
            sendMessage(message);
        } else {
            SendMessage message = generateMessage("У вас нет забронированных комнат.", chatId);
            message.setReplyMarkup(generateKeyboardMarkup(1, new String[]{"Назад"}));

            sendMessage(message);
        }
    }


    private void cancelBookingOption(long chatId, String username) {
        cancelBookingOption(chatId, username, "", false);
    }

    private void cancelBookingOption(long chatId, String username, String inputText, boolean deletingPrevious) {
        UserData userData = usersData.get(username);
        if (deletingPrevious) {
            Booking booking = (Booking) userData.getPossibleAnswers().get(inputText);
            server.removeBooking(booking.getRoom(), booking.getDate(), booking.getHour(), ConnectionType.TELEGRAM, username);
        }

        Booking[] bookings = server.getBookings(username);
        String[] buttons = new String[bookings.length+1];

        userData.getPossibleAnswers().clear();
        for (int i = 0; i < bookings.length; i ++) {
            buttons[i] = bookings[i].getRoom().getName() + " " + bookings[i].getDate();
            userData.getPossibleAnswers().put((bookings[i].getRoom().getName() + " " + bookings[i].getDate()).toLowerCase(), bookings[i]);
        }
        buttons[buttons.length-1] = "Назад";

        userData.setSessionType(SessionType.CANCEL_BOOKINGS);
        SendMessage message = generateMessage("Выберите бронь для отмены", chatId);
        message.setReplyMarkup(generateKeyboardMarkup(1, buttons));
        sendMessage(message);
    }

    private void unknownCommand(long chatId, String username) {
        sendMessage(generateMessage("Неизвестная команда, попробуйте ещё раз", chatId));

        switch (usersData.get(username).getSessionType()) {
            case MAIN -> startOption(chatId, username);
            case BUILDING_CHOICE -> bookingBuildingOption(chatId, usersData.get(username));
            case ROOM_CHOICE -> bookRoomOption(chatId, username);
        }
    }

    private ReplyKeyboardMarkup generateKeyboardMarkup(int width, String[] buttonMap) {
        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder().build();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        int buttonCount = 0;
        for (String button : buttonMap) {
            if (!Objects.equals(button, "null")) {
                buttonCount++;
                if (buttonCount >= width) {
                    keyboard.add(row);
                    row = new KeyboardRow();
                    buttonCount = 0;
                }
                row.add(button);
            }
        }
        if (!row.isEmpty()) {
            keyboard.add(row);
        }

        keyboardMarkup.setIsPersistent(true);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }

    private SendMessage generateMessage(String text, long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            server.log(e);
        }
    }
}
@Setter
@Getter
@NoArgsConstructor
class UserData {
    private TelegramBot.SessionType sessionType;
    private Building building;
    private Room room;
    private LocalDate date;
    private int startHour;
    private HashMap<String, Object> possibleAnswers = new HashMap<>();
}