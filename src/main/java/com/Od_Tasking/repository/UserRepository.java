package com.Od_Tasking.repository;


import com.Od_Tasking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByUserName(String userName);
    Optional<User> findById(String id);
//    User findByIdAndIsDeletedFalse(Long id);
//    List<User> findAllByIsDeletedFalse();
//    User findByUserNameAndIsDeletedFalse(String userName);
}
