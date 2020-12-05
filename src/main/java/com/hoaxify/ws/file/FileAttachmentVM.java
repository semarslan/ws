package com.hoaxify.ws.file;


import lombok.Data;

@Data
public class FileAttachmentVM {
    private String name;

    private String fileType;

    public FileAttachmentVM (FileAttachment fileAttachment) {
        this.setName(fileAttachment.getName());
        this.fileType = fileAttachment.getFileType();
    }
}
