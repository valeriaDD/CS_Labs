package com.web.webserver.cs.service;

import com.web.webserver.cs.models.User;
import com.web.webserver.cs.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(final String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
}
