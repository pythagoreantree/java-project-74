package hexlet.code.services;

import hexlet.code.dto.UserDto;
import hexlet.code.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User createNewUser(UserDto userDto);

    User updateUser(Long id, UserDto userDto);

//    String getCurrentUserName();

//    User getCurrentUser();
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
