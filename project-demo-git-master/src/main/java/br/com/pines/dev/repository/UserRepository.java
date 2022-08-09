package br.com.pines.dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pines.dev.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
	Optional<Users> findByUsername(String username);
}