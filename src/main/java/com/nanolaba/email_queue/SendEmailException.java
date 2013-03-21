package com.nanolaba.email_queue;

public class SendEmailException extends Exception {

    private static final long serialVersionUID = -1372595597350815137L;

    public SendEmailException() {/**/}

    public SendEmailException(String message) {
        super(message);
    }

    public SendEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendEmailException(Throwable cause) {
        super(cause);
    }
}
