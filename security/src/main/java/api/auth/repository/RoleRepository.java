package api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import api.auth.entity.Role;

@Component
public interface RoleRepository extends JpaRepository<Role, Integer> { 

}

