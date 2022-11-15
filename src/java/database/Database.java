package database;

import database.records.User;

import java.util.HashMap;

public class Database {
    private final HashMap<String, User> storage = new HashMap<>();

    public User getUser (String email) {
        return storage.get(email);
    }

    public void addUser(String email, User user) {
        storage.put(email, user);
    }
}
