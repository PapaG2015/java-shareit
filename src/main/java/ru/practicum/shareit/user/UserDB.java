package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.EmailException;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserDB {
    private Map<Integer, User> idUserDB = new HashMap<>();
    private Map<String, User> emailUserDB = new HashMap<>();
    private int counter = 0;

    public User addUser(User user) {
        if (emailUserDB.containsKey(user.getEmail())) throw new EmailException("email is busy");

        counter = counter + 1;
        user.setId(counter);

        idUserDB.put(counter, user);
        emailUserDB.put(user.getEmail(), user);
        return idUserDB.get(counter);
    }

    public User changeUser(User user) {
        int id = user.getId();

        String oldEmail = idUserDB.get(id).getEmail();
        String newEmail = user.getEmail();

        User baseUser = idUserDB.get(id);

        if (emailUserDB.containsKey(newEmail)) throw new EmailException("email is busy");

        if (user.getName() != null) baseUser.setName(user.getName());
        if (user.getEmail() != null) baseUser.setEmail(user.getEmail());

        emailUserDB.remove(oldEmail);
        emailUserDB.put(newEmail, baseUser);

        return idUserDB.get(id);
    }

    public User getUser(int id) {
        return idUserDB.get(id);
    }

    public void deleteUser(int id) {
        String email = idUserDB.get(id).getEmail();

        idUserDB.remove(id);
        emailUserDB.remove(email);
    }

    public List<User> getUsers() {
        return idUserDB.values().stream().collect(Collectors.toList());
    }

    public boolean checkId(int userId) {
        return idUserDB.containsKey(userId);
    }
}
