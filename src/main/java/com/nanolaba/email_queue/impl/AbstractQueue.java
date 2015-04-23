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


    private String smtpHost;
    private String senderEmail;
    private String senderName;
    private String smtpLogin;
    private String smtpPassword;
    private Integer smtpPort = 25;
    private Integer connectionTimeout = 30000;
    private Integer timeout = 30000;
    private Boolean startTls = false;
    private Boolean auth = true;
    private String charset = "UTF-8";

    private Properties defaultProperties = new Properties();

    private int maxErrorQueueLength = 1000;

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
            message.setFrom(new InternetAddress(senderEmail, senderName, charset));
            for (EmailAddress emailAddress : emailMessage.getReceivers()) {
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(emailAddress.getEmail(),
                                emailAddress.getName(), charset)
                );
            }
            List<InternetAddress> replyTo = new LinkedList<InternetAddress>();
            for (EmailAddress emailAddress : emailMessage.getReplyTo()) {
                replyTo.add(new InternetAddress(emailAddress.getEmail(),
                        emailAddress.getName(), charset));
            }

            if (!replyTo.isEmpty()) {
                message.setReplyTo(replyTo.toArray(new Address[replyTo.size()]));
            }

            message.setSubject(emailMessage.getTitle());

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(emailMessage.getMessage(), charset, emailMessage.getMimeSubtype());
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
            if (errors.size() >= maxErrorQueueLength && !errors.isEmpty()) {
                errors.remove(0);
            }
            errors.add(new ErrorDescription(emailMessage, t));
        }
    }

    protected Properties createConnectionProperties() {
        Properties props = getDefaultProperties();

        if (props == null) {
            props = new Properties();
        }

        if (startTls != null) {
            props.setProperty("mail.smtp.starttls.enable", String.valueOf(startTls));
        }
        if (smtpHost != null) {
            props.setProperty("mail.smtp.host", smtpHost);
        }
        if (smtpLogin != null) {
            props.setProperty("mail.smtp.user", smtpLogin);
        }
        if (smtpPassword != null) {
            props.setProperty("mail.smtp.password", smtpPassword);
        }
        if (smtpPort != null) {
            props.setProperty("mail.smtp.port", String.valueOf(smtpPort));
        }
        if (auth != null) {
            props.setProperty("mail.smtp.auth", String.valueOf(auth));
        }
        if (charset != null) {
            props.setProperty("mail.mime.charset", charset);
        }
        if (connectionTimeout != null) {
            props.setProperty("mail.smtp.connectiontimeout", String.valueOf(connectionTimeout));
        }
        if (timeout != null) {
            props.setProperty("mail.smtp.timeout", String.valueOf(timeout));
        }
        return props;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSmtpLogin() {
        return smtpLogin;
    }

    public void setSmtpLogin(String smtpLogin) {
        this.smtpLogin = smtpLogin;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public Boolean isStartTls() {
        return startTls;
    }

    public void setStartTls(Boolean startTls) {
        this.startTls = startTls;
    }

    public Boolean isAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Properties getDefaultProperties() {
        return defaultProperties;
    }

    public void setDefaultProperties(Properties defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    public int getMaxErrorQueueLength() {
        return maxErrorQueueLength;
    }

    public void setMaxErrorQueueLength(int maxErrorQueueLength) {
        this.maxErrorQueueLength = maxErrorQueueLength;
    }
}
