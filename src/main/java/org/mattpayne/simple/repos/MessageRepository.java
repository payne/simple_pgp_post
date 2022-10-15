package org.mattpayne.simple.repos;

import org.mattpayne.simple.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Long> {
}
