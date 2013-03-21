package com.nanolaba.email_queue;

import java.util.List;

public class QueueManager {

    private List<EmailQueue> queues;

    private boolean run = false;

    public void init() {
        run = true;
        runShedulers();
    }

    private void destroy() {
        run = false;
    }

    protected void runShedulers() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (run) {
                    try {
                        flushQueues();
                        Thread.sleep(1000L);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "Email queues manager thread");
        thread.setDaemon(true);
        thread.start();
    }

    protected void flushQueues() throws SendEmailException {
        for (EmailQueue emailQueue : queues) {
            emailQueue.flush();
            for (ErrorDescription errorDescription : emailQueue.getErrors()) {
                onError(errorDescription);
            }
            emailQueue.getErrors().clear();
        }
    }

    protected void onError(ErrorDescription errorDescription) {
        errorDescription.getCause().printStackTrace();
    }

    public List<EmailQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<EmailQueue> queues) {
        this.queues = queues;
    }
}
