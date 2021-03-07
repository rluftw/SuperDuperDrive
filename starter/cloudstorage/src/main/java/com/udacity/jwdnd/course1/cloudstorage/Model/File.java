package com.udacity.jwdnd.course1.cloudstorage.Model;

public class File {
    private Integer id;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private byte[] fileData;

    public File(Integer id, String fileName, String contentType, String fileSize, Integer userId, byte[] fileData) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }
}