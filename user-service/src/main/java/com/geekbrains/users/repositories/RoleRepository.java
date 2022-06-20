package com.geekbrains.users.repositories;

import contract.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findOneByName(String theRoleName);
}
