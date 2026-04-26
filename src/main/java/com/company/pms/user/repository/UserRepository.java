package com.company.pms.user.repository;

import com.company.pms.auth.entity.AuthUser;
import com.company.pms.common.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByEmail(String email);

    List<AuthUser> findByRole(Role role);
}
