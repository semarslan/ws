package com.hoaxify.ws.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//Long id'nin tipi
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
