package com.rined.smalltalk.repositories;

import com.rined.smalltalk.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
