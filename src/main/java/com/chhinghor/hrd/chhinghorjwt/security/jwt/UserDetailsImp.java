package com.chhinghor.hrd.chhinghorjwt.security.jwt;

import com.chhinghor.hrd.chhinghorjwt.model.user.AuthUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImp implements UserDetails {

    private Long id;
    String username;
    @JsonIgnore
    String password;
//    String roles;
    private Collection<? extends GrantedAuthority> authorities;
    // User ( username , password and role )
    public  static UserDetailsImp build(AuthUser user){

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(e-> new SimpleGrantedAuthority(e.getRole().name()))
                .collect(Collectors.toList());

        System.out.println("----- Here are the value fo the authorities :---");
        authorities.stream().map(String::valueOf).forEach(System.out::println);

        return new UserDetailsImp(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
