package com.hoaxify.ws.file;


import lombok.Data;

@Data
public class FileAttachmentVM {
    private String name;

    public FileAttachmentVM (FileAttachment fileAttachment) {
        this.setName(fileAttachment.getName());
    }
}
