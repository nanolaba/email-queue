//package com.nanolaba.email_queue.impl;
//
//import com.nanolaba.email_queue.EmailMessage;
//import com.nanolaba.email_queue.SendEmailException;
//import org.apache.commons.io.FileUtils;
//
//import java.io.*;
//import java.util.Random;
//
//public class EmailQueueFileImpl extends AbstractQueue {
//
//
//    public static final Random RANDOM = new Random();
//
//
//    private String folder;
//
//    @Override
//    public void add(EmailMessage ailMessage) throws SendEmailException {
//        File file = new File(folder);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//
//        try {
//            File messageFile = new File(folder + "/" + System.currentTimeMillis() + RANDOM.nextInt());
//            FileUtils.writeByteArrayToFile(messageFile, toByteArray(emailMessage));
//        } catch (IOException e) {
//            throw new SendEmailException(e);
//        }
//
//    }
//
//    @Override
//    public synchronized void flush() throws SendEmailException {
//
//        File dir = new File(folder);
//        if (dir.exists()) {
//            for (File file : dir.listFiles()) {
//                try {
//                    EmailMessage message = (EmailMessage) toObject(FileUtils.readFileToByteArray(file));
//                    send(message);
//                    file.delete();
//                } catch (Exception e) {
//                    LOGGER.error("Can't send message", e);
//                }
//            }
//        }
//    }
//
//    public byte[] toByteArray(Object obj) throws IOException {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//        ObjectOutputStream oos = new ObjectOutputStream(bos);
//        oos.writeObject(obj);
//        oos.flush();
//        oos.close();
//        bos.close();
//
//        return bos.toByteArray();
//    }
//
//    public Object toObject(byte[] bytes) throws ClassNotFoundException, IOException {
//
//        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
//        ObjectInputStream ois = new ObjectInputStream(bis);
//
//        return ois.readObject();
//    }
//
//    public String getFolder() {
//        return folder;
//    }
//
//    public void setFolder(String folder) {
//        this.folder = folder;
//    }
//}
