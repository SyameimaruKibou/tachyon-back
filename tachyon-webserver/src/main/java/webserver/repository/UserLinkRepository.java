package webserver.repository;

import webserver.model.dao.UserLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLinkRepository extends JpaRepository<UserLink,Long> {

}
