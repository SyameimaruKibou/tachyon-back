package webserver.repository;

import webserver.model.dao.BroadCast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BroadCastRepository extends JpaRepository<BroadCast,Long> {

}
