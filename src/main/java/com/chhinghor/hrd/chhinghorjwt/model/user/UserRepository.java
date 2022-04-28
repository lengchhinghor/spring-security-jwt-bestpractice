package com.chhinghor.hrd.chhinghorjwt.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<AuthUser> findByUsername(String username);
    Boolean existsByUsername(String username);


}
