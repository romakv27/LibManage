package ru.study.library.api;

import ru.study.library.enums.Status;
import ru.study.library.model.User;

public interface IDataProvider {

    public Status createUser(User user);

}
