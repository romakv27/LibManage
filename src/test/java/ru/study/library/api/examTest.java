package ru.study.library.api;

import org.junit.Test;
import ru.study.library.model.User;

import static org.junit.Assert.assertEquals;
import static ru.study.library.enums.Status.SUCCESS;

public class examTest {
    final IDataProvider provider = new DataProviderXML();

    public User getUser(){
        return new User(1L, "Ivan",  18);
    }

    @Test
    public void createUser() {
        User user = getUser();
        assertEquals(provider.createUser(user), SUCCESS);
    }
}
