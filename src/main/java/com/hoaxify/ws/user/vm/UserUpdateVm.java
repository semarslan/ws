package com.hoaxify.ws.user.vm;

import com.hoaxify.ws.shared.FileType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateVm {

    @NotNull
    @Size(min=4, max=255)
    private String displayName;

    @FileType(types={"jpeg", "png"})
    private String image;
}
