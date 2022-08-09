package br.com.pines.dev.repository;

import br.com.pines.dev.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}