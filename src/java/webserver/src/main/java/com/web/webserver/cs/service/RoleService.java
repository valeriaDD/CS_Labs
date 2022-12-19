package com.web.webserver.cs.service;

import com.web.webserver.cs.models.ERole;
import com.web.webserver.cs.models.Role;
import com.web.webserver.cs.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByName(ERole role){
        return roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }
}
