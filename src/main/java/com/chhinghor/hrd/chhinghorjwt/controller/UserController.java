package com.chhinghor.hrd.chhinghorjwt.controller;


import com.chhinghor.hrd.chhinghorjwt.enums.ErrorCode;
import com.chhinghor.hrd.chhinghorjwt.model.user.User;
import com.chhinghor.hrd.chhinghorjwt.model.user.UserRepository;
import com.chhinghor.hrd.chhinghorjwt.payload.user.UserRequest;
import com.chhinghor.hrd.chhinghorjwt.security.jwt.UserDetailsImp;
import com.chhinghor.hrd.chhinghorjwt.service.user.UserServiceImp;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    UserRepository userRepository;
    private UserServiceImp userServiceImp;

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserServiceImp(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    @Secured("ADMIN")
    public ResponseEntity<?> getAll(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImp user){
        try {
            String currentUser = user.getUsername();
            String currentRole = String.valueOf(user.getAuthorities());
            if(currentUser.equals("") || currentUser == null) throw new Exception();
            System.out.println("show my role" + currentRole);
            return ResponseEntity.ok(userServiceImp.getAll());

        }catch (Exception sqlException) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("status ", "false");
            map.put("message ", ErrorCode.EXCEPTION_ACCORS.getMessage());
            System.out.println("SQL Exception : " + sqlException.getMessage());
            return ResponseEntity.ok().body(map);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id)
            throws Throwable {
        try {
            return ResponseEntity.ok(userServiceImp.getOne(id));
        } catch (Exception ex) {
            ex.getMessage();
            return new ResponseEntity(ErrorCode.ITEM_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> insertUser(
          @RequestBody UserRequest userRequest) {
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            User user;
            user = userServiceImp.insert(userRequest);
            map.put("status ", ErrorCode.POST_SUCCESS.getStatus());
            map.put("message", ErrorCode.POST_SUCCESS.getMessage());
            map.put("data", user);
            return ResponseEntity.ok().body(map);
        } catch (Throwable e) {
            e.printStackTrace();
            return ResponseEntity.ok().build();
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserRequest userRequest) throws Throwable{
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            userServiceImp.update(userRequest, id);
            map.put("status", ErrorCode.UPDATED_SUCCESS.getStatus());
            map.put("message", ErrorCode.UPDATED_SUCCESS.getMessage());
            map.put("data", userServiceImp.getOne(id));
            return ResponseEntity.ok().body(map);
        }catch (Exception e) {
            e.getMessage();
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id, @RequestBody UserRequest userRequest) throws Throwable{
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            Optional<User> newUsers = userRepository.findById(id);
            map.put("status", ErrorCode.UPDATED_SUCCESS.getStatus());
            map.put("message", ErrorCode.UPDATED_SUCCESS.getMessage());
            map.put("data", userRepository.findById(id));
            return ResponseEntity.ok().body(map);
        }catch (Exception e) {
            e.getMessage();
            return ResponseEntity.ok().build();
        }
    }


}
