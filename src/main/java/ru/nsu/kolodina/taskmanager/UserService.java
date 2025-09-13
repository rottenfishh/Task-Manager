package ru.nsu.kolodina.taskmanager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void createUser(String Username, String Password) {
        User user = new User();
        user.setUserName(Username);
        user.setPassword(passwordEncoder.encode(Password));
        userRepository.save(user);
    }

}
