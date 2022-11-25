package ru.study.library.api;

import ru.study.library.enums.Status;
import ru.study.library.model.User;

import java.util.Optional;

public interface IDataProvider {

    Status createUser(User user);

    Optional<User> getUserById(Long userId);

    Status updateUser(User user);

    Status deleteUserById(Long userId);
}
