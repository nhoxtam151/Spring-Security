package com.ducku.repository;

import com.ducku.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByUsername(String username);
}
