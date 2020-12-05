package com.hoaxify.ws.hoax.vm;

import com.hoaxify.ws.file.FileAttachment;
import com.hoaxify.ws.file.FileAttachmentVM;
import com.hoaxify.ws.hoax.Hoax;
import com.hoaxify.ws.user.vm.UserVM;
import lombok.Data;

import java.io.File;


@Data
public class HoaxVM {

    private long id;

    private String content;

    private long date;

    private UserVM user;

    private FileAttachmentVM fileAttachmentVM;

    public  HoaxVM(Hoax hoax) {
        this.setId(hoax.getId());
        this.setContent(hoax.getContent());
        this.setDate(hoax.getDate().getTime());
        this.setUser(new UserVM(hoax.getUser()));
        if (hoax.getFileAttachment() != null ){
            this.fileAttachmentVM = new FileAttachmentVM(hoax.getFileAttachment());

        }
    }
}
