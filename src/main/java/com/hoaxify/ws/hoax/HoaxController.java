package com.hoaxify.ws.hoax;


import com.hoaxify.ws.hoax.vm.HoaxVM;
import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.shared.GenericResponse;
import com.hoaxify.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class HoaxController {

    @Autowired
    HoaxService hoaxService;

    @PostMapping("/hoaxes")
    public GenericResponse saveHoax(@Valid @RequestBody Hoax hoax, @CurrentUser User user) {
        hoaxService.save(hoax, user);
        return new GenericResponse("Hoax is saved");
    }

    @GetMapping("/hoaxes")
    Page<HoaxVM> getHoaxes(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return hoaxService.list(page).map(HoaxVM::new);
    }


    @GetMapping("/hoaxes/{id:[0-9]+}")
    ResponseEntity<?> getHoaxesRelative(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
                                        @PathVariable long id,
                                        @RequestParam(name = "count", required = false, defaultValue = "false") boolean count) {

        if (count) {
            long newHoaxCount = hoaxService.getNewHoaxesCount(id);
            Map<String, Long> response = new HashMap<>();
            response.put("count", newHoaxCount);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(hoaxService.getOldHoaxes(id, page).map(HoaxVM::new));
    }


    @GetMapping("/users/{username}/hoaxes")
    Page<HoaxVM> getUserHoaxes(@PathVariable String username,
                               @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page){

        return hoaxService.getHoaxesOfUser(username, page).map(HoaxVM::new);
    }

    @GetMapping("/users/{username}/hoaxes/{id:[0-9]+}")
    ResponseEntity<?> getUserHoaxesRelative(@PathVariable long id,
                                       @PathVariable String username,
                                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
                                       @RequestParam(name = "count", required = false, defaultValue = "false") boolean count) {

        if (count) {
            long newUserHoaxCount = hoaxService.getUserNewHoaxesCount(id, username);
            Map<String, Long> response = new HashMap<>();
            response.put("content", newUserHoaxCount);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(hoaxService.getOldHoaxesOfUser(id, username, page).map(HoaxVM::new));
    }
}
