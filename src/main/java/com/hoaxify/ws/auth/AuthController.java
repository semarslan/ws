package com.hoaxify.ws.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.shared.Views;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    UserRepository userRepository;
    //private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@CurrentUser User user) {


        /*userDetails.getUsername();*/

        /*
            !!!!
        ecurityContextHolder.getContext().getAuthentication().getPrincipal() kullandıktan sonjra
        user bilgilerini alabilir hale geldik.
                split etmemize vs gerek kalmadı bu nedenle

                !!!!
                */

    /*    String base64encoded = authorization.split("Basic ")[1];
        String decoded = new String(Base64.getDecoder().decode(base64encoded)); //user1:P4ssword
        String[] parts = decoded.split(":"); // ["user1", "P4ssword"]*/
        //String username = parts[0];
        /*String username = userDetails.getUsername();
        User inDB = userRepository.findByUsername(username);*/

        return ResponseEntity.ok(user);
    }
}
