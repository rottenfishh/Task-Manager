package ru.nsu.kolodina.taskmanager;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
public class TaskUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // JPA репозиторий

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUsersByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        System.out.println("User loaded: " + user.getUsername() + ", password hash: " + user.getPassword());

        return user;
    }

}
