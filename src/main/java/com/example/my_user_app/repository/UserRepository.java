package com.example.my_user_app.repository;

import com.example.my_user_app.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
   Optional<UserEntity> findByIdAndStatusNot(Long id, Integer statusId);
   @Query("SELECT u FROM UserEntity u WHERE u.status <> :statusId")
   Page<UserEntity> findAllActiveUsers(Pageable pageable,Integer statusId);
}
