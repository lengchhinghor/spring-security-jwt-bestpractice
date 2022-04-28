package com.chhinghor.hrd.chhinghorjwt.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class SignUpDto {

    private String firstname;
    private String lastname;
    private String username;
    private String gender;
    private String phoneNumber;
    private String password;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date updatedAt;
    @JsonIgnore
    private Boolean isActive;
//    @JsonIgnore
    private Set<String> roles;

}