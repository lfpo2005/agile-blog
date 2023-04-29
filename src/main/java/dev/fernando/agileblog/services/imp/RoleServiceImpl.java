package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.enums.RoleType;
import dev.fernando.agileblog.models.RoleModel;
import dev.fernando.agileblog.repositories.RoleRepository;
import dev.fernando.agileblog.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<RoleModel> findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType);
    }
}
