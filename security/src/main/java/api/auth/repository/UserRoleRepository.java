package api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import api.auth.entity.UserRole;


@Component
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
