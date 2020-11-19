package com.hoaxify.ws.hoax;

import com.hoaxify.ws.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HoaxService {

    HoaxRepository hoaxRepository;
    HoaxService(HoaxRepository hoaxRepository) {
        this.hoaxRepository = hoaxRepository;
    }

    public void save(Hoax hoax, User user) {
        hoax.setDate(new Date());
        hoax.setUser(user);
        hoaxRepository.save(hoax);
    }

    public Page<Hoax> list(Pageable page) {
       /* if(hoax != null) {
            return hoaxRepository.findByHoaxNot(hoax.getContent(), page);
        }*/
        return hoaxRepository.findAll(page);
    }
}
