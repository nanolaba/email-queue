package com.nanolaba.email_queue;

import java.io.Serializable;

public class EmailAddress implements Serializable {

    private static final long serialVersionUID = -2072727004950354612L;

    private String email;
    private String name;

    public EmailAddress() {
    }

    public EmailAddress(String email) {
        this.email = email;
    }

    public EmailAddress(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
