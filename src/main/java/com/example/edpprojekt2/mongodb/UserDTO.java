package com.example.edpprojekt2.mongodb;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

public class UserDTO {
    private ObjectId id;
    private String username;
    private String email;
    private String password;
    private String lastLogged;

    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.lastLogged = new Date().toString();
    }

    public UserDTO(String username, String email, String password, String lastLogged) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.lastLogged = lastLogged;
    }

    public UserDTO(ObjectId id, String username, String email, String password, String lastLogged) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.lastLogged = lastLogged;
    }

    public ObjectId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLastLogged() {
        return lastLogged;
    }

    public void setLastLogged() {
        this.lastLogged = new Date().toString();
    }

    public Document toDocument() {
        return new Document("username", username).append("email", email).append("password", password).append("lastLogged", lastLogged);
    }
}
