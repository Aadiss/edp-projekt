package com.example.edpprojekt2.credentials;

import com.example.edpprojekt2.mongodb.UserDTO;

public class LoginStateSingleton {
    private static LoginStateSingleton INSTANCE;
    private String info = "Initial info class";

    private UserDTO loggedUser;

    private LoginStateSingleton() {
    }

    public static LoginStateSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new LoginStateSingleton();
        }

        return INSTANCE;
    }

    public UserDTO getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(UserDTO loggedUser) {
        this.loggedUser = loggedUser;
    }
}
