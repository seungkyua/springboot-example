package com.ask.example.controller;

import lombok.Getter;

@Getter
public class FileUploadResponse {
    final private String fileName;
    final private String downloadUri;
    final private long size;

    public FileUploadResponse(String fileName, String downloadUri, long size) {
        this.fileName = fileName;
        this.downloadUri = downloadUri;
        this.size = size;
    }
}
