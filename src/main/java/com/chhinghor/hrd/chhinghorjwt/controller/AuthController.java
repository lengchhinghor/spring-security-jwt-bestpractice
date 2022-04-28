package com.chhinghor.hrd.chhinghorjwt.controller;

import com.chhinghor.hrd.chhinghorjwt.enums.ErrorCode;
import com.chhinghor.hrd.chhinghorjwt.helper.UserConstant;
import com.chhinghor.hrd.chhinghorjwt.model.user.*;
import com.chhinghor.hrd.chhinghorjwt.payload.user.UserResponse;
import com.chhinghor.hrd.chhinghorjwt.security.jwt.JwtUtils;
import com.chhinghor.hrd.chhinghorjwt.security.jwt.UserDetailsImp;
import com.chhinghor.hrd.chhinghorjwt.service.user.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private UserRepository userRepository;

    private RoleRepository roleRepository;
    UserServiceImp userService;

    @Autowired
    void setUserService(UserServiceImp userService){
        this.userService = userService;
    }
    @Autowired
    void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    void setRoleRepository(RoleRepository roleRepository) { this.roleRepository = roleRepository; }
    private PasswordEncoder passwordEncoder;

    @Autowired
    void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private AuthenticationManager authenticationManager;

    @Autowired
    void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    PasswordEncoder encoder;

    @Autowired
    void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    JwtUtils jwtUtils;

    @Autowired
    void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> authenticateUserByPhone(@RequestBody UserLoginDTO loginDto) {
        {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);
                UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

                Map<String,Object> map = new LinkedHashMap<>();
                map.put("status", ErrorCode.POST_SUCCESS.getStatus());
                map.put("message", ErrorCode.POST_SUCCESS.getMessage());
                map.put("data", new ResponseBasePayload(userRepository.findByUsername(userDetails.getUsername()).get(),
                        userDetails.getUsername(),
                        jwt)
                );
                return ResponseEntity.ok().body(map);
            }catch (RuntimeException ex) {
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("status", ErrorCode.BAD_REQUEST.getStatus());
                map.put("message", "Wrong Username or Password :-)");
                return ResponseEntity.ok().body(map);
            }

        }
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody SignUpDto signUpDto){
//        try {

            if(userRepository.existsByUsername(signUpDto.getUsername())){
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("status", "This username already taken!");
            return ResponseEntity.ok().body(map);
            }

            Set<String> strRoles = Collections.singleton("member");
            Set<Role> roles = new HashSet<>();

            if(strRoles == null || strRoles.equals("") || strRoles.isEmpty()){
                Role userRole = roleRepository.findByRole(ERole.USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                System.out.println(userRole);
                roles.add(userRole);
            }

            User user = new User();
            user.setUsername(signUpDto.getUsername());
            user.setPhoneNumber(signUpDto.getPhoneNumber());
            user.setFirstname(signUpDto.getFirstname());
            user.setLastname(signUpDto.getLastname());
            user.setGender(signUpDto.getGender());
            user.setCreatedAt(signUpDto.getCreatedAt());
            user.setUpdatedAt(signUpDto.getUpdatedAt());
            user.setIsActive(signUpDto.getIsActive());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            System.out.println(userRepository.save(user));
            user.setRoles(roles);
            userRepository.saveAndFlush(user);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpDto.getPhoneNumber(), signUpDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            Map<String,Object> map = new LinkedHashMap<>();
            map.put("status", ErrorCode.POST_SUCCESS.getStatus());
            map.put("message", ErrorCode.POST_SUCCESS.getMessage());
            map.put("data", new UserResponse(
                            user.getId(),
                            user.getFirstname(),
                            user.getLastname(),
                            user.getUsername(),
                            user.getGender(),
                            user.getPhoneNumber(),
                            user.getCreatedAt(),
                            user.getUpdatedAt(),
                            user.getIsActive()

                    )
            );
//            map.put("token", jwt);

            return ResponseEntity.ok().body(map);
//        } catch (RuntimeException ex) {
//            Map<String,Object> map = new LinkedHashMap<>();
//            map.put("status", ErrorCode.BAD_REQUEST.getStatus());
//            map.put("message", ErrorCode.BAD_REQUEST.getMessage());
//            return ResponseEntity.ok().body(map);
//        }
    }
}
