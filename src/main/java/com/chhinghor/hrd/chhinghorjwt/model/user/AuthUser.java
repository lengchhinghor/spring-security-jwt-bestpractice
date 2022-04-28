package com.chhinghor.hrd.chhinghorjwt.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
//@AllArgsConstructor
//@NoArgsConstructor

public class AuthUser {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String firstname;
    private String lastname;
    private String gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", timezone = "Asia/Phnom_Penh")
    @CreationTimestamp
    protected Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", timezone = "Asia/Phnom_Penh")
    @UpdateTimestamp
    protected Date updatedAt;

    private Boolean isActive;
    private Set<AuthRole> roles = new HashSet<>();

    public AuthUser(Long id,
                    String username,
                    String password,
                    String firstname,
                    String lastname,
                    String gender,
                    Date createdAt,
                    Date updatedAt,
                    Boolean isActive) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }
}