package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

@DataJpaTest
public class JpaUser {

    //@Autowired
    //private TestEntityManager em;

    @Autowired
    private UserRepository userRepository;


    @Test
    void verifyRepositoryByPersistingAnEmployee() {
        User user = new User(2, "marat", "marat@yandex.ru");


        userRepository.save(user);
        Assertions.assertNotNull(user.getId());
    }
}
