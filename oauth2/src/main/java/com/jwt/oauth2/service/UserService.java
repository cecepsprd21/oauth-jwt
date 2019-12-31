package com.jwt.oauth2.service;

import com.jwt.oauth2.model.UserEntity;
import com.jwt.oauth2.repository.UserRepository;
import com.jwt.oauth2.response.ResponseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity getUserInfoByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public ResponseEntity<ResponseDao> getAllUser(){
        ResponseDao responseDao = new ResponseDao();
        List<UserEntity> data = userRepository.findAll();
        responseDao.setCode(200);
        responseDao.setStatus("SUCCESS");
        responseDao.setMessage("List User : ");
        responseDao.setData(data);
        return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(responseDao);
    }


    public UserEntity getUserById(Integer id){
        return userRepository.findById(id);
    }

    //CREATE USER SERVICE
    public ResponseEntity<ResponseDao> createUser(UserEntity data){
        data.setPassword(new BCryptPasswordEncoder().encode(data.getPassword()));
        userRepository.save(data);
        ResponseDao responseDao = new ResponseDao();
        responseDao.setCode(201);
        responseDao.setStatus("CREATED");
        responseDao.setMessage("Successfully created");
        responseDao.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDao);
    }

}
