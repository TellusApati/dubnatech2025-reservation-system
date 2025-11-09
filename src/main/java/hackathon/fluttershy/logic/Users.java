package hackathon.fluttershy.logic;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;


public class Users implements UserDetailsService {

    private final HashMap<String, UserDetails> userMap = new HashMap<>();

    public Users() {}

    public boolean addUser(@NonNull UserDetails user) {
        if (userMap.containsKey(user.getUsername())) return false;
        userMap.put(user.getUsername(), user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMap.get(username);
    }
}
