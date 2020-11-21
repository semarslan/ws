package com.hoaxify.ws.hoax;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

@Service
public class HoaxService {

    HoaxRepository hoaxRepository;
    UserService userService;

    HoaxService(HoaxRepository hoaxRepository, UserService userService) {
        this.hoaxRepository = hoaxRepository;
        this.userService = userService;
    }

    public void save(Hoax hoax, User user) {
        hoax.setDate(new Date());
        hoax.setUser(user);
        hoaxRepository.save(hoax);
    }

    public Page<Hoax> list(Pageable page) {
        return hoaxRepository.findAll(page);
    }

    public Page<Hoax> getHoaxesOfUser(String username, Pageable page) {
        User inDB = userService.getByUsername(username);
        return hoaxRepository.findByUser(inDB, page);
    }

    public Page<Hoax> getOldHoaxes(long id, Pageable page) {
        return hoaxRepository.findByIdLessThan(id, page);
    }

    public Page<Hoax> getOldHoaxesOfUser(long id, String username, Pageable page) {
        User inDB = userService.getByUsername(username);
        return hoaxRepository.findByIdLessThanAndUser(id, inDB, page);
    }

    public long getNewHoaxesCount(long id) {
        return hoaxRepository.countByIdGreaterThan(id);
    }

    public long getNewHoaxesCountofUser(long id, String username) {
        User inDB = userService.getByUsername(username);
        return hoaxRepository.countByIdGreaterThanAndUser(id, inDB);
    }

    public List<Hoax> getNewHoaxes(long id, Sort sort) {
        return hoaxRepository.findByIdGreaterThan(id, sort);
    }

    public List<Hoax> getNewHoaxesofUser(long id, String username, Sort sort) {
        User inDB = userService.getByUsername(username);
        return hoaxRepository.findByIdGreaterThanAndUser(id, inDB, sort);
    }
}
