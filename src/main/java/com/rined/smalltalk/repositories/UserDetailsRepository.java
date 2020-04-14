package com.rined.smalltalk.repositories;

import com.rined.smalltalk.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<User, String> {
    // жадно подгружать поля
    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    Optional<User> findById(String id);
}
