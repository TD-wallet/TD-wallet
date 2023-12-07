package org.example.models;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private final List<Account> accounts;

    public User(int id, String username, String email, String password, List<Account> accounts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accounts = accounts;
    }

    public User(String username, String email, String password, List<Account> accounts) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.accounts = accounts;
    }

    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accounts = List.of();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.accounts = List.of();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accounts=" + accounts +
                '}';
    }

}
