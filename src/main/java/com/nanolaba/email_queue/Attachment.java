package com.nanolaba.email_queue;

import java.io.Serializable;

public class Attachment implements Serializable {

    private String filename;
    private byte[] data;
    private String mime = "application/octet-stream";

    public Attachment() {
    }

    public Attachment(String filename, byte[] data) {
        this.filename = filename;
        this.data = data;
    }

    public Attachment(String filename, byte[] data, String mime) {
        this.filename = filename;
        this.data = data;
        this.mime = mime;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}
