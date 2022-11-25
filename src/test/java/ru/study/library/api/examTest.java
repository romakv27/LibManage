package ru.study.library.api;

import org.junit.Test;
import ru.study.library.model.User;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static ru.study.library.enums.Status.FAIL;
import static ru.study.library.enums.Status.SUCCESS;

public class examTest {
    final IDataProvider provider = new DataProviderXML();

    @Test
    public void createUser() {
        User user = new User(1L, "Ivan",  58);
        assertEquals(provider.createUser(user), SUCCESS);

        User user2 = new User(1L, "Ivan",  58);
        assertEquals(provider.createUser(user2), FAIL);
    }
    @Test
    public void getUserById() {
        Optional<? extends User> optionalBook2 = provider.getUserById(1L);
        System.out.println(optionalBook2);
    }

    @Test
    public void upd() {
        User user = new User(1L, "Ivan",  48);
        assertEquals(provider.updateUser(user), SUCCESS);

        User user2 = new User(12L, "Ivan",  48);
        assertEquals(provider.updateUser(user2), FAIL);
    }

    @Test
    public void delUser() {
        User user = new User(1L, "Ivan",  58);
        assertEquals(provider.deleteUserById(user.getId()), SUCCESS);

//        User user2 = new User(21L, "Ivan",  58);
//        assertEquals(provider.deleteUserById(user2.getId()), FAIL);
    }
}
