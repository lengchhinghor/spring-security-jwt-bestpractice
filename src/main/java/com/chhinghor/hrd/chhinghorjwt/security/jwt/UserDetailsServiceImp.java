package com.chhinghor.hrd.chhinghorjwt.security.jwt;

import com.chhinghor.hrd.chhinghorjwt.model.user.AuthUser;
import com.chhinghor.hrd.chhinghorjwt.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AuthUser> selectedUser = userRepository.findByUsername(username);

        if (!selectedUser.isPresent()){
            return null;
        }else {
            UserDetailsImp userDetailsImp = new  UserDetailsImp();
            userDetailsImp.setUsername(selectedUser.get().getUsername());
            userDetailsImp.setPassword((selectedUser.get().getPassword()));
            List<GrantedAuthority> authorities = selectedUser.get().getRoles()
                    .stream()
                    .map(e-> new SimpleGrantedAuthority(e.getRole().name()))
                    .collect(Collectors.toList());
            userDetailsImp.setAuthorities(authorities);
            return userDetailsImp;
        }
    }
//
}
