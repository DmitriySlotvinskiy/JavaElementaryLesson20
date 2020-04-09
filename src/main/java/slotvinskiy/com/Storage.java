package slotvinskiy.com;

import java.util.List;

public interface Storage {

    void removeAll();

    void removeUser(int id);

    void removeUserByName(String name);

    void addUser(User user);

    void updateUser(User user);

    User getUser(int id);

    List<User> getAllUsers();

}
