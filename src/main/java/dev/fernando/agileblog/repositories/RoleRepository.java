package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.enums.RoleType;
import dev.fernando.agileblog.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository <RoleModel, UUID> {

       Optional<RoleModel> findByRoleName(RoleType name);
}
