package com.web.webserver.cs.service;

import com.web.webserver.cs.models.ERole;
import com.web.webserver.cs.models.Role;
import com.web.webserver.cs.models.User;
import com.web.webserver.cs.request.LoginRequest;
import com.web.webserver.cs.request.RegisterRequest;
import com.web.webserver.cs.response.JwtResponse;
import com.web.webserver.cs.security.JwtUtils;
import com.web.webserver.cs.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final RoleService roleService;
    private final MfaService mfaService;

    private final SecurityContext sc = SecurityContextHolder.getContext();

    public byte[] login(LoginRequest loginRequest) throws AuthException {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        sc.setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String secret = mfaService.generateSecret();
        userService.save(
                userService.findByEmail(userDetails.getEmail())
        );

        return mfaService.generateQrPng(secret, userDetails.getEmail());
    }

    public ResponseEntity<?> confirm(String code) throws Exception {
        Object principal;
        if(sc.getAuthentication() != null)
            principal = sc.getAuthentication().getPrincipal();
        else throw new Exception();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        User currentUser = userService.findByEmail(email);

        if (mfaService.verify(currentUser.getSecret(), code)) {
            String jwt = jwtUtils.generateJwtToken(sc.getAuthentication());

            return ResponseEntity.ok(new JwtResponse(jwt));
        }

        throw new AuthException();
    }

    public void register(RegisterRequest registerRequest) throws Exception {
        if (userService.existsByEmail(registerRequest.email())) {
            throw new Exception();
        }

        User user = new User( registerRequest.email(), encoder.encode(registerRequest.password()));

        Set<String> strRoles = registerRequest.role();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.CLASSIC);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("classic")) {
                    Role classic = roleService.findByName(ERole.CLASSIC);
                    roles.add(classic);
                } else {
                    Role asymmetric = roleService.findByName(ERole.ASYMMETRIC);
                    roles.add(asymmetric);
                }
            });
        }

        user.setRoles(roles);
        userService.save(user);
    }
}
