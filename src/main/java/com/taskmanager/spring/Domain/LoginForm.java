package com.taskmanager.spring.Domain;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm
{
    @NotEmpty
    private String username;
    @NotEmpty
    private  String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
