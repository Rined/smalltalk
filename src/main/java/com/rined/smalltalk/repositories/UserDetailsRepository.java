package com.rined.smalltalk.repositories;

import com.rined.smalltalk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<User, String> {
}
