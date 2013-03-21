package com.nanolaba.email_queue;

public class ErrorDescription {

    private Throwable cause;
    private EmailMessage message;

    public ErrorDescription() {
    }

    public ErrorDescription(EmailMessage message, Throwable cause) {
        this.cause = cause;
        this.message = message;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public EmailMessage getMessage() {
        return message;
    }

    public void setMessage(EmailMessage message) {
        this.message = message;
    }
}
