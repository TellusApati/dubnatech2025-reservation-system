package hackathon.fluttershy.data.repository;


import hackathon.fluttershy.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // JPQL Вместо сырого SQL`я
    @Query("SELECT u FROM User u WHERE u.login = :login AND u.password = :password")
    User findUserByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    @Query("SELECT COUNT(u) FROM User u WHERE u.login = :login AND u.password = :password")
    Long countUsersByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    @Query("SELECT u.id FROM User u")
    List<Long> findAllUserId();

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.id = :userId")
    Optional<User> findByIdWithRole(@Param("userId") Long userId);
}


