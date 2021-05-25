package webserver.repository;

import webserver.model.dao.ProjectCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCheckPointRepository extends JpaRepository<ProjectCheckpoint,Long> {

}
