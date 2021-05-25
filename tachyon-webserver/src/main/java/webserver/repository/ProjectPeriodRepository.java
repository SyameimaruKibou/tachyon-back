package webserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webserver.model.dao.ProjectPeriod;

public interface ProjectPeriodRepository extends JpaRepository<ProjectPeriod,Long> {

}
