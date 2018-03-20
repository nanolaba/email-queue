package com.nanolaba.email_queue;

import javax.mail.Message;
import java.io.Serializable;

public class EmailAddress implements Serializable {

    private Message.RecipientType type = Message.RecipientType.TO;
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

    public EmailAddress(String email, String name, Message.RecipientType type) {
        this.email = email;
        this.name = name;
        this.type = type;
    }

    public Message.RecipientType getType() {
        return type;
    }

    public void setType(Message.RecipientType type) {
        this.type = type;
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
