package com.nanolaba.email_queue;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class EmailMessage implements Serializable {

    private static final long serialVersionUID = 6000369195172777354L;

    private String receiverEmail;
    private String receiverName;
    private String title;
    private String message;
    private String mimeSubtype = "plain";

    private List<Attachment> attachments = new LinkedList<Attachment>();

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
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
