package com.chhinghor.hrd.chhinghorjwt.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class UserLoginDTO {
    private String username;
//    private String phoneNumber;
    private String password;
}