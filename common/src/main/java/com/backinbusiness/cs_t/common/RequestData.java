package com.backinbusiness.cs_t.common;

public class RequestData {
    private long fileLength;
    private String path;

    public RequestData(long fileLength, String path) {
        this.fileLength = fileLength;
        this.path = path;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "path = " + path +
                ", length = '" + fileLength + '\'' +
                '}';
    }
}
