package com.chhinghor.hrd.chhinghorjwt.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)

public class AuthRole {

    private int id;
    private ERole role;
}
