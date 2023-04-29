package dev.fernando.agileblog.services;

import dev.fernando.agileblog.enums.RoleType;
import dev.fernando.agileblog.models.RoleModel;

import java.util.Optional;

public interface RoleService {

    Optional<RoleModel> findByRoleName(RoleType roleType);
}
