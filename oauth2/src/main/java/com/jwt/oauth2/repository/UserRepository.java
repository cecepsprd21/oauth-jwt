package com.jwt.oauth2.repository;

import com.jwt.oauth2.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findById(Integer id);
    public List<UserEntity> findAllByActive(boolean state);
    public UserEntity findByUserName(String userName);
}