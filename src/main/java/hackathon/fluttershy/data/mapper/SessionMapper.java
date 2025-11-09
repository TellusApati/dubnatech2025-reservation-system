package hackathon.fluttershy.data.mapper;


import hackathon.fluttershy.data.dto.SessionDto;
import hackathon.fluttershy.data.entity.Session;

public class SessionMapper {
    public static SessionDto mapToSessionDto(Session session) {
        return new SessionDto(
          session.getId(),
          session.getType(),
          session.getExternalId(),
          UserMapper.mapToUserDto(session.getUser())
        );
    }

    public static Session mapToSession(SessionDto sessionDto) {
        return new Session(
          sessionDto.getId(),
          sessionDto.getType(),
          sessionDto.getExternalId(),
          UserMapper.mapToUser(sessionDto.getUser())
        );
    }
}
