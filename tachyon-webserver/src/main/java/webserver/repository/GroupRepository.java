package webserver.repository;

import webserver.model.dao.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Organization,Long> {
    List<Organization> findByInvitationCode(String invitationCode);
}
