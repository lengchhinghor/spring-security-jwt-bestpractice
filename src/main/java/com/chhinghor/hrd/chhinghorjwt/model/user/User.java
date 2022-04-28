package com.chhinghor.hrd.chhinghorjwt.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "sd_user")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String username;
    private String gender;
    private String phoneNumber;
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", timezone = "Asia/Phnom_Penh")
    @CreationTimestamp
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", timezone = "Asia/Phnom_Penh")
    @CreationTimestamp
    private Date updatedAt;

    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "sd_user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;


    @Builder
    public User(Long id,
                String firstname,
                String lastname,
                String gender,
                String username,
                String phoneNumber,
                String password,
                Date createdAt,
                Date updatedAt,
                Boolean isActive,
                String email
//                String roles
    ){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.password = password;
        this.isActive = isActive;
        this.phoneNumber = phoneNumber;
//        this.roles =  roles;
    }
}


