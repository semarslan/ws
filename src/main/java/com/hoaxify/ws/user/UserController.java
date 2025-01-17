package com.hoaxify.ws.user;

import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.user.vm.UserUpdateVm;
import com.hoaxify.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hoaxify.ws.shared.GenericResponse;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/1.0")
public class UserController {

	//private static final Logger log = LoggerFactory.getLogger(UserController.class);

//	@Autowired //DI için
//	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@PostMapping("/users")
	public GenericResponse createUser(@Valid @RequestBody User user) { // Jackson Lib.
		userService.save(user);
		return  new GenericResponse("user created");
	}

	@GetMapping("/users")
	Page<UserVM> getUsers(Pageable page, @CurrentUser User user) {
		return userService.getUsers(page, user).map(UserVM::new);
	}

	@GetMapping("/users/{username}")
	UserVM getUser(@PathVariable String username){
		User user = userService.getByUsername(username);
		return new UserVM(user);
	}

	@PutMapping("/users/{username}")
	@PreAuthorize("#username == principal.username") //user login mi değil mi kontrolü, cevabı 403
	UserVM updateUser(@Valid @RequestBody UserUpdateVm updatedUser, @PathVariable String username, @CurrentUser User loggedInUser){
		User user = userService.updateUser(username, updatedUser);
		return new UserVM(user);
	}

	/**
	 * @param username
	 * @return message
	 *
	 *
	 * user silme
	 */
	@DeleteMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	GenericResponse deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		return new GenericResponse("User is removed");
	}
}
