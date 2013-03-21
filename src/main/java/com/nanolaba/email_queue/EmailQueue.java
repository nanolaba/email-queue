package com.nanolaba.email_queue;


import java.util.List;

public interface EmailQueue {

    void add(EmailMessage emailMessage) throws SendEmailException;

    void flush() throws SendEmailException;

    List<ErrorDescription> getErrors();
}
