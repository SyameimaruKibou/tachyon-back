package webserver.repository;

import webserver.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByUsername(String username);
    List<User> findByGroupId(Long groupId);
}
