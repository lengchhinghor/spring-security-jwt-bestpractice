package com.chhinghor.hrd.chhinghorjwt.service.user;

import com.chhinghor.hrd.chhinghorjwt.enums.ErrorCode;
import com.chhinghor.hrd.chhinghorjwt.exception.BusinessException;
import com.chhinghor.hrd.chhinghorjwt.exception.EntityNotFoundException;
import com.chhinghor.hrd.chhinghorjwt.model.user.User;
import com.chhinghor.hrd.chhinghorjwt.model.user.UserRepository;
import com.chhinghor.hrd.chhinghorjwt.payload.ResponseData;
import com.chhinghor.hrd.chhinghorjwt.payload.user.UserRequest;
import com.chhinghor.hrd.chhinghorjwt.payload.user.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Autowired
    void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
    }

    @Override
    public ResponseData<?> getAll() {
        log.info(" This is getall");
        List<User> users =  userRepository.findAll();
        System.out.println(users);
        return new ResponseData<>(users);
    }

    @Override
    public ResponseData<?> getOne(Long id) throws Throwable {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        });

        UserResponse response = UserResponse.builder()
        .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .gender(user.getGender())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .isActive(user.getIsActive())
                .build();
        return new ResponseData<>(user);
    }

    @Override
    public User insert(UserRequest userRequest) throws Throwable {
        User user = userRequest.toModel();
        try {
        user = userRepository.saveAndFlush(user);
        System.out.println(user + "user show");
        return user;
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorCode.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void update(UserRequest userRequest, Long id) throws Throwable {
        User user = userRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException(User.class, "id", id.toString());
        });

        user.setEmail(userRequest.getEmail());
        user.setFirstname(userRequest.getFirstname());
        user.setUsername(userRequest.getUsername());
        user.setGender(userRequest.getGender());
        user.setEmail(userRequest.getEmail());
    }

    @Override
    public void delete(Long id) throws Throwable {

        User user = userRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException(User.class, "id", id.toString());
        });
        user.setIsActive(false);
    }
}
