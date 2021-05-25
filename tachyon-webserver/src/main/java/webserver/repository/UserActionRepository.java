package webserver.repository;

import webserver.model.dao.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActionRepository extends JpaRepository<UserAction,Long> {

}
