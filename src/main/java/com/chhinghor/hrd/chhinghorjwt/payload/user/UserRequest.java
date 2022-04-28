package com.chhinghor.hrd.chhinghorjwt.payload.user;

import com.chhinghor.hrd.chhinghorjwt.model.user.User;
import lombok.Getter;

@Getter
public class UserRequest {

    private String firstname;
    private String lastname;
    private String username;
    private String gender;
    private String email;
    private String password;

    public User toModel() {
        return User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .gender(gender)
                .email(email)
                .password(password)
                .build();
    }
}
