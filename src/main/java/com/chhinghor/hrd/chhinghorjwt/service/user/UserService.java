package com.chhinghor.hrd.chhinghorjwt.service.user;

import com.chhinghor.hrd.chhinghorjwt.model.user.User;
import com.chhinghor.hrd.chhinghorjwt.payload.ResponseData;
import com.chhinghor.hrd.chhinghorjwt.payload.user.UserRequest;

public interface UserService {
    ResponseData<?> getAll();

    ResponseData<?> getOne(Long id) throws Throwable;

    User insert(UserRequest userRequest) throws Throwable;

    void update(UserRequest userRequest, Long id) throws Throwable;

    void delete(Long id) throws Throwable;
}
