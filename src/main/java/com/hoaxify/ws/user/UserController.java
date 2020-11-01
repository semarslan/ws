package com.hoaxify.ws.user;

import com.hoaxify.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.hoaxify.ws.shared.GenericResponse;

import javax.validation.Valid;


@RestController
public class UserController {

	//private static final Logger log = LoggerFactory.getLogger(UserController.class);

//	@Autowired //DI için
//	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@PostMapping("/api/1.0/users")

	public GenericResponse createUser(@Valid @RequestBody User user) { // Jackson Lib.
		//log.info(user.toString());
		/*Validation için*/

		/*
		!!!!!!
		 Artık aşağıdaki bloğa gerek kalmadı. Çünkü Bean Validation kullandık ve
		gerekli işlemler handleValidationException api'nda yapılıyor
		!!!!
		*/
		/*ApiError error = new ApiError(400, "Validation error", "/api/1.0/users");
		Map<String, String> validationErrors = new HashMap<>();

		String username = user.getUsername();
		String displayName = user.getDisplayName();

		if(username == null || username.isEmpty()) {
			validationErrors.put("username", "Username cannot be null");
		}
		if(displayName == null || displayName.isEmpty()) {
			validationErrors.put("displayName", "Cannot be null");
		}
		if(validationErrors.size() > 0) {
			error.setValidationErrors(validationErrors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}*/
		userService.save(user);
//		GenericResponse response = new GenericResponse();
//		response.setMessage("user created"); lombok olmazsa get-set kullanılırsa
		
		//lombok kullanılırsa
		//return new GenericResponse("user created");

		return  new GenericResponse("user created");
	}

	@GetMapping("/api/1.0/users")
	Page<UserVM> getUsers(Pageable page) {
		return userService.getUsers(page).map(UserVM::new);
	}
}
