package com.hoaxify.ws.user;

import org.springframework.data.jpa.repository.JpaRepository;

//Long id'nin tipi
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
