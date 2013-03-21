package com.nanolaba.email_queue.impl;

import com.nanolaba.email_queue.Attachment;
import com.nanolaba.email_queue.EmailAddress;
import com.nanolaba.email_queue.EmailMessage;
import org.junit.Ignore;
import org.junit.Test;

public class EmailQueueMemoryImplTest {


    @Test
    @Ignore
    public void sendMessage() throws Throwable {

        EmailMessage message = new EmailMessage();
        message.getReceivers().add(new EmailAddress("alexander@andriishin.com", "Alexander Andriishin"));
        message.getReplyTo().add(new EmailAddress("alexander@andriishin.com", "replyto"));
        message.setTitle("Привет!");
        message.setMessage("<html><body><h1>Привет!</h1></body></html>");
        message.setMimeSubtype("html");

        Attachment attachment = new Attachment();
        attachment.setFilename("test.txt");
        attachment.setData("Привет".getBytes());
        attachment.setMime("text/plain");
        message.getAttachments().add(attachment);

        EmailQueueMemoryImpl queue = new EmailQueueMemoryImpl();
        queue.setSenderEmail("robot@sudexpa.ru");
        queue.setSenderName("ROBOT");
        queue.setSmtpLogin("robot@sudexpa.ru");
        queue.setSmtpPassword("xxx");
        queue.setSmtpHost("smtp.yandex.ru");
        queue.setSmtpPort(587);
        queue.setStartTls(true);

        queue.send(message);

        if (!queue.getErrors().isEmpty()) {
            throw queue.getErrors().get(0).getCause();
        }
    }
}
