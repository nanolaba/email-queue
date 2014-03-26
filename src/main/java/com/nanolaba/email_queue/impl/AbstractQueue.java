package com.nanolaba.email_queue.impl;

import com.nanolaba.email_queue.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public abstract class AbstractQueue implements EmailQueue {

    protected static final String DEFAULT_CHARSET = "UTF-8";

    private String smtpHost;
    private String senderEmail;
    private String senderName;
    private String smtpLogin;
    private String smtpPassword;
    private int smtpPort = 25;
    private boolean startTls = false;
    private boolean auth = true;

    private final List<ErrorDescription> errors = new LinkedList<ErrorDescription>();

    @Override
    public List<ErrorDescription> getErrors() {
        return errors;
    }

    protected void send(EmailMessage emailMessage) {
        try {
            Properties props = createConnectionProperties();

            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail, senderName, DEFAULT_CHARSET));
            for (EmailAddress emailAddress : emailMessage.getReceivers()) {
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(emailAddress.getEmail(),
                                emailAddress.getName(), DEFAULT_CHARSET)
                );
            }
            List<InternetAddress> replyTo = new LinkedList<InternetAddress>();
            for (EmailAddress emailAddress : emailMessage.getReplyTo()) {
                replyTo.add(new InternetAddress(emailAddress.getEmail(),
                        emailAddress.getName(), DEFAULT_CHARSET));
            }

            if (!replyTo.isEmpty()) {
                message.setReplyTo(replyTo.toArray(new Address[replyTo.size()]));
            }

            message.setSubject(emailMessage.getTitle());

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(emailMessage.getMessage(), DEFAULT_CHARSET, emailMessage.getMimeSubtype());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            for (Attachment attachment : emailMessage.getAttachments()) {
                messageBodyPart = new MimeBodyPart();
                String filename = attachment.getFilename();
                DataSource source = new ByteArrayDataSource(attachment.getData(), attachment.getMime());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);
            }

            message.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect(smtpHost, smtpLogin, smtpPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Throwable t) {
            errors.add(new ErrorDescription(emailMessage, t));
        }
    }

    protected Properties createConnectionProperties() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.starttls.enable", String.valueOf(startTls));
        if (smtpHost != null) {
            props.setProperty("mail.smtp.host", smtpHost);
        }
        if (smtpLogin != null) {
            props.setProperty("mail.smtp.user", smtpLogin);
        }
        if (smtpPassword != null) {
            props.setProperty("mail.smtp.password", smtpPassword);
        }
        props.setProperty("mail.smtp.port", String.valueOf(smtpPort));
        props.setProperty("mail.smtp.auth", String.valueOf(auth));
        props.setProperty("mail.mime.charset", "utf-8");
        return props;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpLogin() {
        return smtpLogin;
    }

    public void setSmtpLogin(String smtpLogin) {
        this.smtpLogin = smtpLogin;
    }

    public boolean isStartTls() {
        return startTls;
    }

    public void setStartTls(boolean startTls) {
        this.startTls = startTls;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
}
