package ru.nsu.kolodina.taskmanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUsersByUserName(String userName);
}
