package com.hoaxify.ws.hoax.vm;

import com.hoaxify.ws.hoax.Hoax;
import com.hoaxify.ws.user.vm.UserVM;
import lombok.Data;


@Data
public class HoaxVM {

    private long id;

    private String content;

    private long date;

    private UserVM user;

    public  HoaxVM(Hoax hoax) {
        this.setId(hoax.getId());
        this.setContent(hoax.getContent());
        this.setDate(hoax.getDate().getTime());
        this.setUser(new UserVM(hoax.getUser()));
    }
}
