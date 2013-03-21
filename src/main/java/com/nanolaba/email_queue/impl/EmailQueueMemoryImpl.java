package com.nanolaba.email_queue.impl;

import com.nanolaba.email_queue.EmailMessage;
import com.nanolaba.email_queue.SendEmailException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * $Revision: 93 $
 * $Author: aandriishin $
 * $Date: 2012-08-10 14:03:18 +0400 (Пт, 10 авг 2012) $
 */
public class EmailQueueMemoryImpl extends AbstractQueue {

    private List<EmailMessage> messages = new LinkedList<EmailMessage>();

    @Override
    public void add(EmailMessage emailMessage) throws SendEmailException {
        messages.add(emailMessage);
    }

    @Override
    public synchronized void flush() throws SendEmailException {

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
