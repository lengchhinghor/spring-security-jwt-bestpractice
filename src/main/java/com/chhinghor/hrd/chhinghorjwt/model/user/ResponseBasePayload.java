package com.chhinghor.hrd.chhinghorjwt.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBasePayload {

    AuthUser user;
    String username;
    String token;
}