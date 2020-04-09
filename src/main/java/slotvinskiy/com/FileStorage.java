package slotvinskiy.com;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage implements Storage {

    private String fileName;
    private List<User> users = new ArrayList<>();
    private int lastId = 0;

    public FileStorage(String fileName) {

        this.fileName = fileName;
        load();
    }

    @Override
    public void removeAll() {
        users.clear();
        save();
    }

    @Override
    public void removeUser(int id) {
        User toDelete = null;
        for (User user : users) {
            if (user.getId() == id) {
                toDelete = user;
                break;
            }
        }
        users.remove(toDelete);
        if (toDelete != null) {
            save();
        }
    }

    @Override
    public void removeUserByName(String name) {

        User toDelete = null;
        for (User user : users) {
            if (user.getName().equals(name)) {
                toDelete = user;
                break;
            }
        }
        users.remove(toDelete);
        if (toDelete != null) {
            save();
        }
    }

    @Override
    public void addUser(User user) {
        User newUser = getUser(user.getId());
        if (newUser == null) {
            users.add(user);
            user.setId(++lastId);
        } else {
            updateUser(newUser);
        }
        save();
    }

    @Override
    public void updateUser(User newUser) {
        for (User user : users) {
            if (newUser.getId() == user.getId()) {
                user.setName(newUser.getName());
                user.setAge(newUser.getAge());
                save();
                break;
            }
        }
    }

    @Override
    public User getUser(int id) {
        User someUser = null;
        for (User user : users) {
            if (user.getId() == id) {
                someUser = user;
                save();
                break;
            }
        }
        return someUser;
    }

    @Override
    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            return null;
        } else {
            return users;
        }
    }

    public int getLastId() {
        return lastId;
    }

    /*  @Override
    public String toString() {
        return "FileStorage{" +
                "fileName='" + fileName + '\'' +
                usersToString() +
                ", lastId=" + lastId +
                '}';
    }*/

    public String toString() {

        StringBuilder sb = new StringBuilder();
        if (users.isEmpty()) {
            return "";
        }
        sb.append("\nUSERS LIST:\n");
        for (User user : users) {
            sb.append(user.toString()).append("\n");
        }
        return sb.toString() + "\nlastId=" + lastId;
    }

    private void save() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(fileName))) {
            bf.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Gson gson = new Gson();
        FileStorage loadData = null;
        String json;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            json = br.readLine();
            if (json != null) {
                loadData = gson.fromJson(json, FileStorage.class);
                users = loadData.getAllUsers();
                lastId = loadData.getLastId();
            }
        } catch (JsonSyntaxException | FileNotFoundException e) {
            System.out.println("Empty load file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
