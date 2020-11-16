package com.hoaxify.ws.hoax;

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

    public void save(Hoax hoax) {
        hoax.setDate(new Date());
        hoaxRepository.save(hoax);
    }

    public Page<Hoax> list(Pageable page) {
       /* if(hoax != null) {
            return hoaxRepository.findByHoaxNot(hoax.getContent(), page);
        }*/
        return hoaxRepository.findAll(page);
    }
}
