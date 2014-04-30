package com.nanolaba.email_queue.impl;

import com.nanolaba.email_queue.EmailMessage;
import com.nanolaba.email_queue.SendEmailException;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class EmailQueueMemoryImpl extends AbstractQueue {

    private final Collection<EmailMessage> messages = new LinkedList<EmailMessage>();

    @Override
    public void add(EmailMessage emailMessage) throws SendEmailException {
        synchronized (messages) {
            messages.add(emailMessage);
        }
    }

    @Override
    public void flush() throws SendEmailException {
        synchronized (messages) {
            Iterator<EmailMessage> iterator = messages.iterator();

            while (iterator.hasNext()) {
                try {
                    EmailMessage message = iterator.next();
                    send(message);
                } finally {
                    iterator.remove();
                }
            }
        }
    }
}
