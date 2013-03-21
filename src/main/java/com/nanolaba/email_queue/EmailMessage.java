package com.nanolaba.email_queue;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class EmailMessage implements Serializable {

    private static final long serialVersionUID = 6000369195172777354L;

    private List<EmailAddress> receivers = new LinkedList<EmailAddress>();
    private List<EmailAddress> replyTo = new LinkedList<EmailAddress>();
    private String title;
    private String message;
    private String mimeSubtype = "plain";

    private List<Attachment> attachments = new LinkedList<Attachment>();

    public List<EmailAddress> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<EmailAddress> receivers) {
        this.receivers = receivers;
    }

    public List<EmailAddress> getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(List<EmailAddress> replyTo) {
        this.replyTo = replyTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getMimeSubtype() {
        return mimeSubtype;
    }

    public void setMimeSubtype(String mimeSubtype) {
        this.mimeSubtype = mimeSubtype;
    }
}
